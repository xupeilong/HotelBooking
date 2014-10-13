# coding=utf-8
import scrapy
from scrapy.contrib.pipeline.images import ImagesPipeline
import mysql.connector
from HotelScrapy.items import HouseInfo
from HotelScrapy.items import HotelInfo 

class InfoImagePipeline(ImagesPipeline):

    def get_media_requests(self, item, info):
        url = item['image_url']
        if url != 'none':
            yield scrapy.Request(item['image_url'])
        else:
            item['image_file_name'] = 'none'

    def item_completed(self, results, item, info):
        image_paths = [x['path'] for ok, x in results if ok]
        image_name = 'none'
        if len(image_paths) > 0:
            image_name = str(image_paths[0]).replace('full/', '')
        item['image_file_name'] = image_name
        return item


class HotelInfoPipeline(object):

    def process_item(self, item, spider):
        if (isinstance(item, HotelInfo)):
            conn = mysql.connector.connect(user='root', password='sdp123', database='hotel', use_unicode=True)
            cursor = conn.cursor()
            v = [item['hotel_id'], item['level'], item['area'], item['image_file_name']]
            cursor.execute('insert into hotel_info(hotel_id, level, area, image_path) values(%s,%s,%s,%s)', v)
            conn.commit()
            cursor.close()
            conn.close()
        return item


class HouseInfoPipeline(object):

    def get_house(self, cursor, hotel_id, house_name):
        sql = 'select * from orderprocess_housingtype where hotelId_id = ' + str(hotel_id) + ' and roomName like \'' + house_name + '\''
        cursor.execute(sql)
        return cursor.fetchall()

    def process_item(self, item, spider):
        if (isinstance(item, HouseInfo)):
            hotel_id = item['hotel_id']
            house_name = item['house_name']
            conn = mysql.connector.connect(user='root', password='sdp123', database='hotel', use_unicode=True)
            cursor = conn.cursor()
            values = []
            values = values + self.get_house(cursor, hotel_id, house_name)
            house_name_1 = house_name[0:-1] + '_'
            values = values + self.get_house(cursor, hotel_id, house_name_1)
            house_name_1 = house_name[0:-1] + '__'
            values = values + self.get_house(cursor, hotel_id, house_name_1)
            house_name_1 = house_name[0:-2] + '_'
            values = values + self.get_house(cursor, hotel_id, house_name_1)
            house_name_1 = house_name[0:-2] + '__'
            values = values + self.get_house(cursor, hotel_id, house_name_1)
            for v in values:
                cursor.execute('select * from house_info where house_id = ' + str(v[0]))
                vv = cursor.fetchall()
                if len(vv) > 0:
                    continue
                v = [v[0], item['image_file_name']]
                cursor.execute('insert into house_info(house_id, image_path) values(%s,%s)', v)

            conn.commit()
            cursor.close()
            conn.close()
            
        return item



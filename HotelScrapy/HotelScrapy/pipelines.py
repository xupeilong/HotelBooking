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

            cursor.execute("select * from app_city where city_name = '" + item['city_name'] + "'")
            values = cursor.fetchall()
            if len(values):
                current_count = values[0][2]
                cursor.execute("update app_city set hotel_count = " + str(current_count + 1) + " where city_name = '" + item['city_name'] + "'")
            else:
                cursor.execute("insert into app_city(city_name, hotel_count) values(%s,%s)", [item['city_name'], 1])
            conn.commit()

            cursor.execute("select * from OrderProcess_hotel where id = " + str(item['hotel_id']))
            source_name = cursor.fetchall()[0][1]

            v = [item['hotel_id'], item['level'], item['area'], item['image_file_name'], source_name, item['real_name']]
            cursor.execute('insert into app_hotel_info(hotel_id, level, area, image_path, source_name, real_name) values(%s,%s,%s,%s,%s,%s)', v)

            conn.commit()
            cursor.close()
            conn.close()
        return item


class HouseInfoPipeline(object):

    def get_house(self, cursor, hotel_id, house_name):
        sql = 'select * from OrderProcess_housingtype where hotelId_id = ' + str(hotel_id) + ' and roomName like \'' + house_name + '\''
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
                cursor.execute('select * from app_house_info where house_id = ' + str(v[0]))
                vv = cursor.fetchall()
                if len(vv) > 0:
                    continue
                v = [v[0], item['image_file_name']]
                cursor.execute('insert into app_house_info(house_id, image_path) values(%s,%s)', v)

            conn.commit()
            cursor.close()
            conn.close()
            
        return item



import scrapy
from scrapy.contrib.pipeline.images import ImagesPipeline
import mysql.connector

class MyImagesPipeline(ImagesPipeline):

    def get_media_requests(self, item, info):
        for image_url in item['image_urls']:
            yield scrapy.Request(image_url)

    def item_completed(self, results, item, info):
        image_paths = [x['path'] for ok, x in results if ok]

        conn = mysql.connector.connect(user='root', password='sdp123', database='hotel', use_unicode=True)
        cursor = conn.cursor()
        v = [item['hotel_id'], item['level'], item['area'], str(image_paths[0])]
        cursor.execute('insert into hotel_info(hotel_id, level, area, image_path) values(%s,%s,%s,%s)', v)

        conn.commit()
        cursor.close()
        conn.close()

        return item

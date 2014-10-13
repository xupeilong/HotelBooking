# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class HotelInfo(scrapy.Item):
    image_url = scrapy.Field()
    image_file_name = scrapy.Field()
    hotel_id = scrapy.Field()
    area = scrapy.Field()
    level = scrapy.Field()

class HouseInfo(scrapy.Item):
    image_url = scrapy.Field()
    image_file_name = scrapy.Field()
    house_name = scrapy.Field()
    house_id = scrapy.Field()
    hotel_id = scrapy.Field()





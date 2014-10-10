# -*- coding: utf-8 -*-

# Scrapy settings for HotelScrapy project
#
# For simplicity, this file contains only the most important settings by
# default. All the other settings are documented here:
#
#     http://doc.scrapy.org/en/latest/topics/settings.html
#

BOT_NAME = 'HotelScrapy'

SPIDER_MODULES = ['HotelScrapy.spiders']
NEWSPIDER_MODULE = 'HotelScrapy.spiders'

ITEM_PIPELINES = {'HotelScrapy.pipelines.MyImagesPipeline': 1}
IMAGES_STORE = 'D:/images'

# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'HotelScrapy (+http://www.yourdomain.com)'

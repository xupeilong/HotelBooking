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

ITEM_PIPELINES = {'HotelScrapy.pipelines.InfoImagePipeline': 1,
    'HotelScrapy.pipelines.HotelInfoPipeline': 2,
    'HotelScrapy.pipelines.HouseInfoPipeline': 3,
}
IMAGES_STORE = 'D:/images'
# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'HotelScrapy (+http://www.yourdomain.com)'
USER_AGENT = 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36'

COOKIES = {" zdatactrip": "zdatactrip=14622a672cade5f4418c91b18c717b7e", " returncash": "2", " zdata": "zdata=nFK1QOd676t/zYl3MSmUeU3nmYs=", " _ga": "GA1.2.2040832501.1412253478", " HotelDomesticVisitedHotels1": "436187=664268,410,4.3,5601,/hotel/437000/436187/0d4dc5223099409e99fe0c459c1b3c59.jpg,/hotel/665000/664268/dd33d03d109248b48838d3ceb73636c5.jpg&427091=532749,448,4.5,3787,/t1/hotel/86000/85893/c50d95f9b02e4ecbaf6eec9c27f0ea63.jpg,/hotel/533000/532749/20358630ac6a4ea28b14f915301e850a.jpg&469544=88130,475,4.6,2683,/hotel/140000/139708/6b7bb9a5d9fb4f94a980aa1451a81cd2.jpg,/hotel/89000/88130/7408be1e02df46a7838d470cf6b980cc.jpg&426748=88130,475,4.6,2373,/hotel/427000/426352/5b1098dea55840ba99a3689b041a32c0.jpg,/hotel/89000/88130/7408be1e02df46a7838d470cf6b980cc.jpg&396387=1410680,409,4.4,3896,/hotel/19000/18709/89d9e395870440868eaaf0a1bec7a4ef.jpg,/t1/hotel/1411000/1410680/438450c936944395bd75b862ef41c391.jpg", " _jzqco": "%7C%7C%7C%7C1423644960942%7C1.1721722089.1412253484466.1423726133999.1423726657840.1423726133999.1423726657840.undefined.0.0.110.110", " NSC_WT_Ipufmt_80": "ffffffff09001c6545525d5f4f58455e445a4a423660", " _bfs": "1.9", "_abtest_userid": "168a4090-aa94-4581-8140-980025d3b509", " ASP.NET_SessionId": "qqfhq2nddvxopflusj1rpw2e", " HotelCityID": "1split%E5%8C%97%E4%BA%ACsplitBeijingsplit2015-02-12split2015-02-13split0", " _bfa": "1.1412253477911.3ahdxx.1.1423717731749.1423723903066.18.209", " Session": "SmartLinkCode=U1686&SmartLinkKeyWord=&SmartLinkQuary=&SmartLinkHost=&SmartLinkLanguage=zh", " _bfi": "p1%3D102003%26p2%3D102003%26v1%3D208%26v2%3D207", " initIndexSearch": "1229=%25e8%258b%258f%25e6%25a2%2585%25e5%25b2%259b%25ef%25bc%258c%25e7%25b4%25a0%25e5%258f%25bb%25e4%25bb%2596%25e5%25b0%25bc%25ef%25bc%258c%25e6%25b3%25b0%25e5%259b%25bd%257ckohsamui%257cisNew", " __utmz": "1.1415456521.7.4.utmcsr=baidu|utmccn=baidu81|utmcmd=cpc|utmctr=%E6%90%BA%E7%A8%8B", " _abtest_": "21eadcc0-a40e-4b43-bc20-0597ca71e39c", " productcode": "0000#3`10010#4`01110#8`10011#B`1101#F`01111#L`11110#M`10110#Q`1000#a`11101#d`01101#e`1010#g`0011#j`11111#l`0010#n`10111#o`11000#p`11001#t`11100#v`01100#x`010#y`0001#z@00101010111110101000111001011000110001111101001010000110010111000001101110010101001111010111001011000011111101111011010", " __zpspc": "9.13.1423723903.1423726657.8%233%7Cwww.btspread.com%7C%7C%7C%7C%23", " bid": "bid=F", " ASP.NET_SessionSvc": "MTAuOC45Mi4xNzZ8OTA5MHxqaW5xaWFvfGRlZmF1bHR8MTQyMzEyNzI5NzUwOQ", " __utma": "1.2040832501.1412253478.1415456521.1415459847.8"}
# {
#     "_abtest_userid": r'168a4090-aa94-4581-8140-980025d3b509',
#     '_abtest_': r'21eadcc0-a40e-4b43-bc20-0597ca71e39c', 
#     'initIndexSearch': r'1229=%25e8%258b%258f%25e6%25a2%2585%25e5%25b2%259b%25ef%25bc%258c%25e7%25b4%25a0%25e5%258f%25bb%25e4%25bb%2596%25e5%25b0%25bc%25ef%25bc%258c%25e6%25b3%25b0%25e5%259b%25bd%257ckohsamui%257cisNew',
#     '__utma': r'1.2040832501.1412253478.1415456521.1415459847.8',
#     '__utmz': r'1.1415456521.7.4.utmcsr=baidu|utmccn=baidu81|utmcmd=cpc|utmctr=%E6%90%BA%E7%A8%8B',
#     '_ga': r'GA1.2.2040832501.1412253478',
#     'returncash': r'2',
#     'Session': r'SmartLinkCode=U1686&SmartLinkKeyWord=&SmartLinkQuary=&SmartLinkHost=&SmartLinkLanguage=zh',
#     'ASP.NET_SessionSvc': r'MTAuOC4xMTUuNDF8OTA5MHxqaW5xaWFvfGRlZmF1bHR8MTQyMDM3NTIxODg4OQ',
#     'NSC_WT_Ipufmt_80': r'ffffffff09001c3c45525d5f4f58455e445a4a423660',
#     'ASP.NET_SessionId': r'vf5qgudvdoefqxsld5pfr2d1',
#     '__zpspc': r'9.9.1423669173.1423669173.2%233%7Cwww.btspread.com%7C%7C%7C%7C%23',
#     '_jzqco': r'%7C%7C%7C%7C1423644960942%7C1.1721722089.1412253484466.1423669173094.1423669173591.1423669173094.1423669173591.undefined.0.0.88.88',
#     'HotelDomesticVisitedHotels1': r'436187=664268,410,4.3,5601,/hotel/437000/436187/0d4dc5223099409e99fe0c459c1b3c59.jpg,/hotel/665000/664268/dd33d03d109248b48838d3ceb73636c5.jpg&427091=532749,448,4.5,3781,/t1/hotel/86000/85893/c50d95f9b02e4ecbaf6eec9c27f0ea63.jpg,/hotel/533000/532749/20358630ac6a4ea28b14f915301e850a.jpg&469544=88130,475,4.6,2683,/hotel/140000/139708/6b7bb9a5d9fb4f94a980aa1451a81cd2.jpg,/hotel/89000/88130/7408be1e02df46a7838d470cf6b980cc.jpg&426748=88130,475,4.6,2373,/hotel/427000/426352/5b1098dea55840ba99a3689b041a32c0.jpg,/hotel/89000/88130/7408be1e02df46a7838d470cf6b980cc.jpg&396387=1410680,409,4.4,3896,/hotel/19000/18709/89d9e395870440868eaaf0a1bec7a4ef.jpg,/t1/hotel/1411000/1410680/438450c936944395bd75b862ef41c391.jpg',
#     # 'HotelDomesticVisitedHotels1': r'436187=664268,410,4.3,5590,/hotel/437000/436187/0d4dc5223099409e99fe0c459c1b3c59.jpg,/hotel/665000/664268/dd33d03d109248b48838d3ceb73636c5.jpg&427091=532749,448,4.5,3781,/t1/hotel/86000/85893/c50d95f9b02e4ecbaf6eec9c27f0ea63.jpg,/hotel/533000/532749/20358630ac6a4ea28b14f915301e850a.jpg&469544=88130,475,4.6,2683,/hotel/140000/139708/6b7bb9a5d9fb4f94a980aa1451a81cd2.jpg,/hotel/89000/88130/7408be1e02df46a7838d470cf6b980cc.jpg&426748=88130,475,4.6,2373,/hotel/427000/426352/5b1098dea55840ba99a3689b041a32c0.jpg,/hotel/89000/88130/7408be1e02df46a7838d470cf6b980cc.jpg&396387=1410680,409,4.4,3896,/hotel/19000/18709/89d9e395870440868eaaf0a1bec7a4ef.jpg,/t1/hotel/1411000/1410680/438450c936944395bd75b862ef41c391.jpg',
#     'productcode': r'1100#1`001#B`111#S`101#T`010#U`011#r`100#s`000#t`1101#y@11010110101111011110010001001100',
#     '_bfa': r'1.1412253477911.3ahdxx.1.1423653775369.1423669172507.14.186',
#     '_bfs': r'1.3',
#     '_bfi': r'p1%3D102003%26p2%3D102003%26v1%3D186%26v2%3D185'
# }




HEADERS = {
    "Accept": "*/*",
    "Accept-Encoding": "gzip, deflate, sdch",
    "Accept-Language": "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4",
    "Cache-Control": "max-age=0",
    "Connection": "keep-alive",
    "Content-Type": "application/x-www-form-urlencoded; charset=utf-8",

    "Host": "hotels.ctrip.com",
    "User-Agent": "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36"
    }

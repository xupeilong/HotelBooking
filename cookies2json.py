import json



cookies_str = r'_abtest_userid=168a4090-aa94-4581-8140-980025d3b509; _abtest_=21eadcc0-a40e-4b43-bc20-0597ca71e39c; initIndexSearch=1229=%25e8%258b%258f%25e6%25a2%2585%25e5%25b2%259b%25ef%25bc%258c%25e7%25b4%25a0%25e5%258f%25bb%25e4%25bb%2596%25e5%25b0%25bc%25ef%25bc%258c%25e6%25b3%25b0%25e5%259b%25bd%257ckohsamui%257cisNew; __utma=1.2040832501.1412253478.1415456521.1415459847.8; __utmz=1.1415456521.7.4.utmcsr=baidu|utmccn=baidu81|utmcmd=cpc|utmctr=%E6%90%BA%E7%A8%8B; _ga=GA1.2.2040832501.1412253478; returncash=2; Session=SmartLinkCode=U1686&SmartLinkKeyWord=&SmartLinkQuary=&SmartLinkHost=&SmartLinkLanguage=zh; ASP.NET_SessionSvc=MTAuOC45Mi4xNzZ8OTA5MHxqaW5xaWFvfGRlZmF1bHR8MTQyMzEyNzI5NzUwOQ; NSC_WT_Ipufmt_80=ffffffff09001c6545525d5f4f58455e445a4a423660; ASP.NET_SessionId=qqfhq2nddvxopflusj1rpw2e; zdata=zdata=nFK1QOd676t/zYl3MSmUeU3nmYs=; bid=bid=F; zdatactrip=zdatactrip=14622a672cade5f4418c91b18c717b7e; HotelCityID=1split%E5%8C%97%E4%BA%ACsplitBeijingsplit2015-02-12split2015-02-13split0; _bfi=p1%3D102003%26p2%3D102003%26v1%3D208%26v2%3D207; __zpspc=9.13.1423723903.1423726657.8%233%7Cwww.btspread.com%7C%7C%7C%7C%23; _jzqco=%7C%7C%7C%7C1423644960942%7C1.1721722089.1412253484466.1423726133999.1423726657840.1423726133999.1423726657840.undefined.0.0.110.110; HotelDomesticVisitedHotels1=436187=664268,410,4.3,5601,/hotel/437000/436187/0d4dc5223099409e99fe0c459c1b3c59.jpg,/hotel/665000/664268/dd33d03d109248b48838d3ceb73636c5.jpg&427091=532749,448,4.5,3787,/t1/hotel/86000/85893/c50d95f9b02e4ecbaf6eec9c27f0ea63.jpg,/hotel/533000/532749/20358630ac6a4ea28b14f915301e850a.jpg&469544=88130,475,4.6,2683,/hotel/140000/139708/6b7bb9a5d9fb4f94a980aa1451a81cd2.jpg,/hotel/89000/88130/7408be1e02df46a7838d470cf6b980cc.jpg&426748=88130,475,4.6,2373,/hotel/427000/426352/5b1098dea55840ba99a3689b041a32c0.jpg,/hotel/89000/88130/7408be1e02df46a7838d470cf6b980cc.jpg&396387=1410680,409,4.4,3896,/hotel/19000/18709/89d9e395870440868eaaf0a1bec7a4ef.jpg,/t1/hotel/1411000/1410680/438450c936944395bd75b862ef41c391.jpg; productcode=0000#3`10010#4`01110#8`10011#B`1101#F`01111#L`11110#M`10110#Q`1000#a`11101#d`01101#e`1010#g`0011#j`11111#l`0010#n`10111#o`11000#p`11001#t`11100#v`01100#x`010#y`0001#z@00101010111110101000111001011000110001111101001010000110010111000001101110010101001111010111001011000011111101111011010; _bfa=1.1412253477911.3ahdxx.1.1423717731749.1423723903066.18.209; _bfs=1.9'
# print cookies_str
cookies = cookies_str.split(';')
ans = dict()
for c in cookies:
    name = c.split('=')[0]
    value = c.replace(name + '=', '')
    ans[name] = value
print len(ans)
j = json.dumps(ans)
f = open('cookies_json.txt', 'w')
f.write(j)
f.close()

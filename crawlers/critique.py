import requests
import json

numfile = open("CSnumbers.txt", "r")
numStr = numfile.read()
numList = numStr.replace("[","").replace(" ","").replace('"','').replace('CS','').split(",")
print(numList)

headers = {
"authority": "c4citk6s9k.execute-api.us-east-1.amazonaws.com",
"method": "GET",
"path": "/prod/data/course?courseID=CS%206260",
"scheme": 'https',
"accept": "*/*",
"accept-encoding": 'gzip, deflate, br',
"accept-language": "zh-CN,zh;q=0.9",
"origin": "https://critique.gatech.edu",
"referer":"https://critique.gatech.edu/",
"sec-ch-ua": '"Chromium";v="94", "Google Chrome";v="94", ";Not A Brand";v="99"',
"sec-ch-ua-mobile": "?0",
"sec-ch-ua-platform": "Windows",
"sec-fetch-dest": "empty",
"sec-fetch-mode": "cors",
"sec-fetch-site": "cross-site",
"user-agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36",
}

result = []
for i in numList:
    data = requests.get(url='https://c4citk6s9k.execute-api.us-east-1.amazonaws.com/prod/data/course?courseID=CS%20'+i,headers=headers).text
    datalist = json.loads(data)
    if (datalist['raw']!=[]):
        datalist["courseNum"] = i
        result.append(datalist)


jsonString = json.dumps(result)
jsonFile = open("courseCritique.json", "w")
jsonFile.write(jsonString)
jsonFile.close()
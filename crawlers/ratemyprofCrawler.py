import requests
from bs4 import BeautifulSoup
import json
import base64
import time
start_time = time.time()
headers={
    "Authorization": "Basic dGVzdDp0ZXN0",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36",
    "Content-Type": "application/json"
}
r = requests.get('https://www.ratemyprofessors.com/search/teachers?query=*&sid=361')


soup = BeautifulSoup(r.text, 'lxml')

info_list = soup.find_all(attrs={'class': 'SearchResultsPage__StyledSearchResultsPage -sc-1srop1v-0 kdXwyM'})
tit_quality = info_list[0].find_all(attrs={'class':'CardNumRating__CardNumRatingNumber-sc-17t4b9u-2'})
tit_rate = info_list[0].find_all(attrs={'class': 'CardFeedback__CardFeedbackNumber-lq6nix-2 hroXqf'})
tit_name=info_list[0].find_all(attrs={'class': 'CardName__StyledCardName-sc-1gyrgim-0 cJdVEK'})
tit_major=info_list[0].find_all(attrs={'class': 'CardSchool__Department-sc-19lmz2k-0 haUIRO'})

print(tit_quality)
result=[]
for i in range(8):
    result.append({
        "name": tit_name[i].text.replace('\n', ''),
        "take_again": tit_rate[2*i].text.replace('\n', ''),
        "diffculty": tit_rate[2*i+1].text.replace('\n', ''),
        "quality": tit_quality[i].text.replace('\n', ''),
        "major": tit_major[i].text.replace('\n', '')
    })

for k in range(359):
    csor="arrayconnection:"+str(k*8+7);
    cursor=base64.b64encode((csor).encode('ascii')).decode('ascii')
    professor_query ={"query":"query TeacherSearchPaginationQuery(\n  $count: Int!\n  $cursor: String\n  $query: TeacherSearchQuery!\n) {\n  search: newSearch {\n    ...TeacherSearchPagination_search_1jWD3d\n  }\n}\n\nfragment TeacherSearchPagination_search_1jWD3d on newSearch {\n  teachers(query: $query, first: $count, after: $cursor) {\n    edges {\n      cursor\n      node {\n        ...TeacherCard_teacher\n        id\n        __typename\n      }\n    }\n    pageInfo {\n      hasNextPage\n      endCursor\n    }\n    resultCount\n  }\n}\n\nfragment TeacherCard_teacher on Teacher {\n  id\n  legacyId\n  avgRating\n  numRatings\n  ...CardFeedback_teacher\n  ...CardSchool_teacher\n  ...CardName_teacher\n  ...TeacherBookmark_teacher\n}\n\nfragment CardFeedback_teacher on Teacher {\n  wouldTakeAgainPercent\n  avgDifficulty\n}\n\nfragment CardSchool_teacher on Teacher {\n  department\n  school {\n    name\n    id\n  }\n}\n\nfragment CardName_teacher on Teacher {\n  firstName\n  lastName\n}\n\nfragment TeacherBookmark_teacher on Teacher {\n  id\n  isSaved\n}\n","variables":{"count":8,"cursor":"YXJyYXljb25uZWN0aW9uOjc=","query":{"text":"","schoolID":"U2Nob29sLTM2MQ=="}}}
    professor_query["variables"]["cursor"]=cursor
    data = requests.post(url="https://www.ratemyprofessors.com/graphql", json=professor_query,headers=headers)
    ex_json=data.json()
    for i in range(8):
        if len(ex_json["data"]["search"]["teachers"]["edges"])>i:
            result.append({
                "name": (ex_json["data"]["search"]["teachers"]["edges"][i]["node"]["firstName"]+" "+ex_json["data"]["search"]["teachers"]["edges"][i]["node"]["lastName"]),
                "take_again": (ex_json["data"]["search"]["teachers"]["edges"][i]["node"]["wouldTakeAgainPercent"]) ,
                "diffculty":(ex_json["data"]["search"]["teachers"]["edges"][i]["node"]["avgDifficulty"]),
                "quality": (ex_json["data"]["search"]["teachers"]["edges"][i]["node"]["avgRating"]),
                "major":(ex_json["data"]["search"]["teachers"]["edges"][i]["node"]["department"]) ,
            })
    print("yes")
jsonString = json.dumps(result)
jsonFile = open("ratemyprofData.json", "w")
jsonFile.write(jsonString)
jsonFile.close()
print("--- %s seconds ---" % (time.time() - start_time))
import json
import math
import requests

file = open('myGithubToken.txt', 'r')
token = file.readline()
file.close()

headers = {"Authorization": 'token ' + token, "Accept": "application/vnd.github.v3+json"}


def getReposData():
    language = 'python'
    url = 'https://api.github.com/search/repositories?q=language:{}&sort=stars&page=1&per_page=1'.format(language)
    r = requests.get(url, headers=headers)
    js_file = open("repos.json", 'w+')
    data = r.json()['items']
    l = len(data)
    js_file.write(json.dumps(data, indent=l))


def getReposIssues():
    url = 'https://api.github.com/repos/{}/issues?state=all&page={}&per_page={}'
    searchIssue = url.format("TheAlgorithms/Python", 1, math.inf)
    r = requests.get(searchIssue, headers=headers)
    l = len(r.json())
    js_file = open("issues.json", 'w+')
    js_file.write(json.dumps(r.json(), indent=l))
    js_file.close()


def getDevelopers():
    url = 'https://api.github.com/repos/{}/contributors?state=all&page={}&per_page={}'
    searchEvent = url.format("TheAlgorithms/Python", 1, math.inf)
    r = requests.get(searchEvent, headers=headers)
    l = len(r.json())
    js_file = open("developers.json", 'w+')
    js_file.write(json.dumps(r.json(), indent=l))
    js_file.close()


def getComments():
    url = 'https://api.github.com/repos/{}/commits?state=all&page={}&per_page={}'
    searchEvent = url.format("TheAlgorithms/Python", 1, math.inf)
    r = requests.get(searchEvent, headers=headers)
    l = len(r.json())
    js_file = open("commits.json", 'w+')
    js_file.write(json.dumps(r.json(), indent=l))
    js_file.close()


def getReleases():
    url = 'https://api.github.com/repos/{}/releases?state=all&page={}&per_page={}'
    searchEvent = url.format("TheAlgorithms/Python", 1, math.inf)
    r = requests.get(searchEvent, headers=headers)
    l = len(r.json())
    js_file = open("releases.json", 'w+')
    js_file.write(json.dumps(r.json(), indent=l))
    js_file.close()


getReposData()
getReposIssues()
getDevelopers()
getComments()
getReleases()

#  gitabel Python3 version by TeamH

"""
You will need to add your authorization token in the code.
Here is how you do it.
1) In terminal run the following command
curl -i -u <your_username> -d '{"scopes": ["repo", "user"], "note": "OpenSciences"}' https://api.github.com/authorizations
2) Enter ur password on prompt. You will get a JSON response. 
In that response there will be a key called "token" . 
Copy the value for that key and paste it on line marked "token" in the attached source code. 
3) Run the python file. 
		 python gitable.py
"""
 

import urllib.request, urllib.error, urllib.parse
import json, csv
import re, datetime
import sys, random, os
 
class L():
	"Anonymous container"
	def __init__(i,**fields) : 
		i.override(fields)
	def override(i,d): i.__dict__.update(d); return i
	def __repr__(i):
		d = i.__dict__
		name = i.__class__.__name__
		return name+'{'+' '.join([':%s %s' % (k,pretty(d[k])) 
										 for k in i.show()])+ '}'
	def show(i):
		lst = [str(k)+" : "+str(v) for k,v in i.__dict__.items() if v != None]
		return ',\t'.join(map(str,lst))
	
def secs(d0):
	d     = datetime.datetime(*list(map(int, re.split('[^\d]', d0)[:-1])))
	epoch = datetime.datetime.utcfromtimestamp(0)
	delta = d - epoch
	return delta.total_seconds()
 
def dump1(u,issues, mapping):
	"""
	requires github access token
	returns dictionary of labeled issues with its list of events.
	"""
	token = "" #can set token here
	
	if token == "<your_token>":
		try:  
			token = os.environ['GITHUB_TOKEN']
		except KeyError: 
			print("Please set the environment variable GITHUB_TOKEN")
			print("In bash: GITHUB_TOKEN=<your_token>;export GITHUB_TOKEN")
			sys.exit(1)
	
	request = urllib.request.Request(u, headers={"Authorization" : "token "+token})
	v = urllib.request.urlopen(request).read()
	w = json.loads(v.decode())
	if not w: 
		return False
	
	random.shuffle(w)
	
	for event in w:

		# print_dict(event)
		# sys.exit(0)

		issue_id = event['issue']['number']
		issue_name = event['issue']['title']
		#created_at = secs(event['created_at'])
		created_at = event['created_at']
		action = event['event']

		label_name = ""
		if event.get('label'):
			label_name = event['label']['name']
		#label_name = event['label']['name']
		
		milestone = event['issue']['milestone']
		m = False
		if milestone != None : 
			m = True
			ms_id = milestone['number']
			ms_desc = milestone['description']
			ms_title = milestone['title']
			ms_user = milestone['creator']['login']
			ms_ctime = milestone['created_at']
			ms_duetime = milestone['due_on']
			ms_closetime = milestone['closed_at']
			ms_status = milestone['state']
		
		user = event['actor']['login']
		if not mapping.get(user):
			mapping[user] = "user{}".format(len(mapping)+1)
		user = mapping[user]

		if milestone != None:
			eventObj = L(when = created_at,
						 action = action,
						 what = label_name,
						 user = user,
						 issuename = issue_name,
						 m = True,
						 milestone = ms_title,
						 ms_id = ms_id,
						 ms_desc = ms_desc,
						 ms_user = ms_user,
						 ms_ctime = ms_ctime,
						 ms_duetime = ms_duetime,
						 ms_closetime = ms_closetime,
						 ms_status = ms_status)
		else:
			eventObj = L(when = created_at,
						 action = action,
						 what = label_name,
						 user = user,
						 issuename = issue_name,
						 m = False)
		
		#issueObj = L(created=created_at)

		if not issues.get(issue_id): 
			issues[issue_id] = []
		issues[issue_id].append(eventObj)
	
	for issue,events in issues.items():
		events.sort(key=lambda event: event.when)

	return True

def print_dict(d):
	for k in d:
		if type(k) is dict:
			print_dict(k)
		else:
			print(str(k)+" : "+str(d[k]))

def dump(u,issues, mapping):
	try:
		return dump1(u, issues, mapping)
	except Exception as e: 
		print(e)
		print("Contact TA")
		return False

def launchDump():
	repos = ['karanjadhav2508/kqsse17',
			'SE17GroupH/Zap', 
			'genterist/whiteWolf'
			]

	with open("private_mappings.csv", 'w', newline='') as file:
		outputWriter = csv.writer(file)
		outputWriter.writerow(['original', 'alias'])
		
	random.shuffle(repos)

	for index,reponame in enumerate(repos):
		page = 1
		issues, mapping = {}, {}
		
		repo_url = 'https://api.github.com/repos/{}/issues/events?page='.format(reponame)+'{}'
		while(dump(repo_url.format(page), issues, mapping)):
			page += 1
		
		group_id = "group{}".format(index+1)
		with open("private_mappings.csv", 'a', newline='') as file:
			outputWriter = csv.writer(file)
			outputWriter.writerow([reponame, group_id])
			for username, user_id in mapping.items():
				outputWriter.writerow([username, user_id])

		# ms_id = milestone['number']
		# 	ms_desc = milestone['description']
		# 	milestone = milestone['title']
		# 	ms_user = milestone['creator']['login']
		# 	ms_ctime = milestone['created_at']
		# 	ms_duetime = milestone['due_on']
		# 	ms_closetime = milestone['closed_at']

		filename = group_id+".csv"
		with open(filename, 'w', newline='') as outputFile:
			outputWriter = csv.writer(outputFile)
			outputWriter.writerow(["issue_id", "issue_name", "when", "action", "what", "user", "milestone_id", "milestone", "description", "milestone_user", "ctime", "dtime", "closetime", "status"])
			for issue, events in issues.items():
				for event in events:
					if event.m:
						outputWriter.writerow([issue, event.issuename, event.when, event.action, event.what, event.user, event.ms_id, event.milestone, event.ms_desc, event.ms_user, event.ms_ctime, event.ms_duetime, event.ms_closetime, event.ms_status])
					else:
						outputWriter.writerow([issue, event.issuename, event.when, event.action, event.what, event.user])



launchDump()

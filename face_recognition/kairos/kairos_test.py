import requests
import json

enroll_url = "https://api.kairos.com/enroll"
verify_url = "https://api.kairos.com/verify"
recognize_url = "https://api.kairos.com/recognize"
remove_url = "https://api.kairos.com/gallery/list_all"

params = {
	'image': 'https://consequenceofsound.files.wordpress.com/2015/12/screen-shot-2015-12-28-at-7-36-21-pm.png',
	'subject_id': 'lemmy',
	'gallery_name': 'musicians'
}
headers = {'app_id': '76395980', 'app_key': 'fe00f9411bbadb0269b4f0757050aaf2'}

r_enroll = requests.post(enroll_url, headers=headers, data=json.dumps(params))
print r_enroll.__dict__, "\n\n"

params['image'] = 'http://www.fuse.tv/image/5681e0bc2232677a1200006d/768/512/photo-of-motorhead-and-lemmy.jpg'
r_verify = requests.post(verify_url, headers=headers, data=json.dumps(params))
print r_verify.__dict__

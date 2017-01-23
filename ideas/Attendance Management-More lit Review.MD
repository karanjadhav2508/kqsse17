



1.	http://ieeexplore.ieee.org.prox.lib.ncsu.edu/abstract/document/7789812/

	a.	Attendance Management System Using a Mobile Device and a Web Application
	
	b.	Network-Based Information Systems (NBiS), 2016 19th International Conference on
	
	c.	7-9 Sept. 2016
	
	d.	Important Key points-
	
		•	For lectures with a large amount of students, there is a “compelling need for a well-designed attendance management system” for efficient tracking of participation.
		•	A common case is the use of a student ID card. IC chips or a magnetic record in the card is used. 
		•	There are several classifications: 
				o	Student ID card and card reader (most common)
			o	Near field communication based system (NFC)
				o	Barcode and similar coding based
			o	Biometrics 
			o	One time password
		•	For ID cards, students may have to stand in line if there are too many students. It is also not cost effective due to card replacement.
		•	NFC uses RFID, BLE Beacon, and wifi.  It needs special equipment like RFID readers or a beacon transmitter. This means that this can also be costly
		•	Barcode or two dimensional coding systems have easy implementation has no requirement for particular devices. Therefore, its cost effective. 
		•	Passwords are also easy, but validity of participation is hard to check. 
		•	This Paper’s Proposition-  To fix the previous problems: combination of mobile device and web service.
			o	During Lecture- a mobile device is passed around to each student.
			o	Student finds his/her id from a list, confirms identity, and chooses registration by selfie or signature. 
				o	Once registered as “in class” that students Id is removed from the list, allowing for and instant view of who’s not there. Considered “unique and useful” according to paper.
			o	All selfies and signatures paired with ID’s are then sent to a “roll sheet database”.
		•	Evaluation of Proposition-
			o	Trial was held in 2015 where about 60 students were tested with the proposed application. 
			o	Most students except for the first day, chose to use signature registration.
			o	Paper forms were also used in parallel, and questions or comments about the system could be written there. 
			o	It was shown that more students prefer the proposed system rather than the form-paper.
			o	Considered “more interesting, convenient, fresh innovative, and special” compared to form paper.
			o	But form was comfortable, allowed feedback and questions, and familiar.
		•	Conclusion of Paper- Main disadvantage is the lack of feedback opportunities.
	e.	MY ANALYSIS so far-  The proposed system may fix some of the problems associated with the different categories listed, such as how it is less costly, and it has a high usability where it can even be used during a lecture. However, it still has some costs in each classroom would require some kind of mobile device and it could be a distraction being passed around during a class. Also, it does not fix the problem of verifying that a particular student is actually in class. One student could just use signature registration for him and a friend. While this is a novel idea, easy to implement, and highly usable (with the exception of feedback encouragement, which is not exactly the focus of this issue), these other drawbacks should be considered. 


2.	http://dl.acm.org.prox.lib.ncsu.edu/citation.cfm?id=2527157

	a.		Student attendance reporting prototype using SSQL
	
	b.	Journal of Computing Sciences in Colleges archive 
	
	c.	Volume 29 Issue 1, October 2013 
	
	d.	Pages 40-49
	
	e.	Important Key points-
	
		•	Proposed Bar code scanner system- for the University of Mississippi
		•	Classroom scanners would need to be integrated with their current data management system. More servers would be needed to handle input data to send to the data management system.  (this is on large scale)
		•	Prototype- 4 scanners in 2 classrooms with quickly put together reporting system to see effectiveness before implementing school wide with the  costs of barcode scanners.
		•	Simple Stream Query Language can be used to “rapidly develop this prototype system”
		•	SSQL is made to run in standalone interpreter and its syntax is like SQL. But adds capabilities to handle data streams. 
		•	This paper also discusses other languages that can be used for data streams
		•	SSQL-
			o	Focus on Packet-bases stream definition- each piece of data is defined as a packet.
			o	This allows multiple packets and packet types per stream, so no need to filter data. 
			o	Features of SSQL-Simple SQL-like Syntax, Packet Based Stream Definition, Window Based Queries, Trigger controlled queries, File based Queries, Simple Stream In/Out/ Export Control, Stream merging, Traditional Query Operations, Interpreter based system
		•	Attendance Reporting Prototype- Simulated experiment, with an app that used 30 lines of code to handle incoming data and export it to a simple easy to read file. 
	f.	MY ANALYSIS so far- This paper goes into depth about SSQL and not much into the implementation of their simulated prototype. However, based on this information and their simulation, we should consider taking advantage of the simplicity of SSQL in some of our own solutions. Their simulation was also to show that the barcode scanner would be a more effective way to take attendance in addition to the management and interpretation of the attendance data for participation grades, final attendance counts, as well as dropping students after a certain amount of missed classes.  This is especially when the class size is large.



3.	http://search.proquest.com.prox.lib.ncsu.edu/docview/1524024677?pq-origsite=gscholar
	a.	TouchIn: An NFC Supported Attendance System in a University Environment
	b.	International Journal of Information and Education Technology; 
	c.	 (Oct 2014): 448-453.
	d.	Key points
		•	Problems with traditional manual paper signing for attendance can include:
			o	Time consuming
			o	Prone to cheating (student signs for a friend)
			o	Necessary for student metrics in grading, and others…
		•	“NFC technology is now integrated into mobile devices which can be used for online payment, access control, user id, and transfer of personal/private info.”
		•	“NFC is new, short range, high frequency, low bandwidth, and wireless communication technology”
			o	Activates by touching 2 NFC devices together.
			o	Max data transfer rate is 424kbits/s at a frequency of 13.56 MHz
			o	Based on RFID, uses initiator and a target. Initiator is active with internal power for IC’s that generate outgoing signal. Target is passive with no power, so could be stickers or cards.
			o	Three modes-
	 Reader/writer mode, NFC device reads or writes to a tag
	card emulation mode, NFC device acts like a card or tag, 
	peer to peer mode. Exchange of info between two NFC devices
	•	Related Work-
		o	Desktop application which had teacher check boxes next to student names
		o	Client server socket program where students registered individually from personal laptop.
		o	Both still waste time but it is easier to generate reports and gather data.
		o	.NET and Oracle based system that implements card readers
		o	Finger print system with handheld device and host application for management of data
		o	BIS has RFID based commercial system. Sends SMS and email to parents automatically. Data sent to BISAM server. Also includes time management part for keeping track of employee attendance.
		o	Technical University of Cartagena developed NFC based system using NFC active and passive devices, NFC readers, where NFC server hosts linked to the university network. Students and teachers install mobile app on their devices, and will run upon touching the reader.
		o	Budapest University of Technology and Economics also implemented NFC system. Biometric info is added to this to reduce impersonation. Student scans a card and their fingerprint. 
		o	Combined NFC and Social Network Service based (mostly for Event management attendance) Goal was to bridge NFC and SNS. Participant provides SNS Id, downloads application upon attendance, The meeting name session name and sns ID is set by participant. Touching the tag in the session, sends info to server, and participant gets all sns IDs in attendance
	•	Proposition of Paper- System that does not require RFID, Finger print, NFC specific readers.  Called TouchIn System. Only need Smart phone
	•	TouchIn- Reader unit and web server unit. 
	•	Software required in the reader unit- client application to be installed on the mobile phone.  For the web unit- web based attendance application.
	•	Reader unit responsible for reading student info. From mobile device. Device Id is used for verifying the student. (assuming student has NFC enabled mobile device and internet connection)
	•	In the case that student does not have nfc enabled device- NFC tag can be issued, like a card, or bracelet. 
	•	Designed with PHP, Javascript, and MySQL. 
		o	Database tables-
	Users
	Course
	Schedule 
	Attendance
		o	Users of app- administrator, lecturers, and students
•	MY ANALYSIS so far- This is an interesting concept where no extra devices are needed. Only “Smart Posters” and the students phone. This paper points out other concepts used in the past.  TouchIn seems like a promising avenue to focus on when finding solutions to attendance management. The main drawback is that when Internet or Smart phones are not available, it may be more difficult to implement without having extra costs (issuing cards, tags..)

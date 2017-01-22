# Attendance management system

- __Finger print based__ : [patent](https://pdfs.semanticscholar.org/2dfb/66245655947a4bf66b1ffb025618e1fbcadc.pdf)
    - Problems:
        - Manual attendance takes time
        - Possibility of impersonation and loss of attendance sheet
        - Manual calculation of attendance % at the end of the term
    - Solution:
        - Finger print scan
        - Compared against a list of pre-registered users
        - Similar paper : http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.163.4880&rep=rep1&type=pdf
    - Other solutions and limitations:
        - Card and card reader
        - iris authentication
        - password based : impersonation problem
        - GSM-GPRS based : position based
        - RFID : card needs to be carried
    - Our Implementation(not reqd at this point):
        - Face recognition app
        - since biometric not possible and ease of availability of mobile phones

- __RFID based__ : [paper](http://ieeexplore.ieee.org/abstract/document/5356360)
    - Similar problems (possibly need actual numbers to prove, same for the first implementation)
    - Scan unique RFID tag on the reader
    - Our implementation : Bar code scanner app (similar UI, might not be acceptable)

import socket
import time
import base64
from ocr_date import receiveImg
import cv2
import os
import cx_Oracle
import numpy as np
from PIL import Image
import io

print("Oracle 접속 시도")
LOCATION = r"C:\instantclient_21_3"
os.environ["PATH"] = LOCATION + ";" + os.environ["PATH"]  # 환경변수 등록
OracleConnect = cx_Oracle.connect('c##product', '1234', '113.198.234.39:1521/xe')
cur = OracleConnect.cursor()
print("DB 접속 완료")

host = '113.198.234.39' # 호스트 ip를 적어주세요
port = 58000           # 포트번호를 임의로 설정해주세요

server_sock = socket.socket(socket.AF_INET)
server_sock.bind((host, port))
server_sock.listen(1)
print("연결 대기 중..")



def get_bytes_stream(sock, length):
    buffer = b''
    try:
        remain = length
        while True:
            data = sock.recv(remain)
            buffer += data
            if len(buffer) == length:
                break
            elif len(buffer) < length:
                remain = length - len(buffer)
    except Exception as e:
        print(e)
    return buffer[:length]

while True: #안드로이드에서 연결 버튼 누를 때까지 기다림
    client_sock, addr = server_sock.accept() # 연결 승인

    print('연결 주소 : ', addr)  # 연결 주소 print

    len_bytes_string = None
    len_bytes = None

    len_bytes_string = bytearray(client_sock.recv(1024))[2:]
    len_bytes = len_bytes_string.decode('utf-8')
    print(len_bytes)
    if len_bytes[0:3] == 'abc':
        name = []

        oracleSql = f'SELECT P_NAME, ST_ FROM PRODUCT WHERE BARCODE = \'' + len_bytes[3:] + '\''
        print(oracleSql)
        cur.execute(oracleSql)
        print(cur)
        result = ''
        for i in cur:
            name.append(i)
        print(name)
        if not name:
            result = ''
        else:
            result = ''.join(name[0])

        print(result)
        result = result.encode("UTF-8")
        client_sock.send(len(result).to_bytes(2, byteorder='big'))
        client_sock.send(result)
        print(result)

        #client_sock.send(result.encode('utf-8'))
        print("데이터 송신 완료")
    else:
        length = int(len_bytes)

        img_bytes = get_bytes_stream(client_sock, length)
        img_path = "./date01.jpg"

        with open(img_path, 'wb') as writer:
            writer.write(img_bytes)
        print(img_path + " is saved")

        d_img = cv2.imread('date01.jpg', cv2.IMREAD_COLOR)
        # 이미지 이진화
        gray = cv2.cvtColor(d_img, cv2.COLOR_BGR2GRAY)
        ret, dst = cv2.threshold(gray, 120, 255, cv2.THRESH_BINARY)
        # cv2.imshow("dst", dst)

        # 구조화 요소 커널, 사각형 (3x3) 생성 ---①
        k = cv2.getStructuringElement(cv2.MORPH_RECT, (2, 2))
        # 침식 연산 적용 ---②
        erosion = cv2.erode(dst, k)

        # 결과 출력
        merged = np.hstack((dst, erosion))
        # cv2.imshow('Erode', merged)

        # cv2.waitKey(0)
        # cv2.destroyAllWindows()

        chars = receiveImg(merged, '418dd09991a16ad0c410f4a38dcf5927')  # 카카오 OCR API
        chars = chars.replace(" ", "")
        result_chars = ''
        for c in chars:
            if c.isdigit():
                result_chars += c
        result_chars = result_chars[-6:]
        print(result_chars)

        # print("결과 : ", result_chars)

        result_chars = result_chars.encode("UTF-8")
        client_sock.send(len(result_chars).to_bytes(2, byteorder='big'))
        client_sock.send(result_chars)
        print(result_chars)
        print("데이터 송신 완료")

cur.close()
OracleConnect.close()

client_sock.close()
server_sock.close()

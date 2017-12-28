<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>APK文件上传</title>

    <style type="text/css">
        .myform {
            width: 400px;
            height: 400px;
            border: 1px solid #f5f418;;
            margin: 0px;
        }

        .myInput {
            position: relative;
            display: inline-block;
            background: #D0EEFF;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            margin-bottom: 20px;
            margin-top: 20px;
            overflow: hidden;
            color: #1E88C7;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
        }

        .myInput input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .myInput:hover {
            background: #AADFFD;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }

        #sbutton {
            position: relative;
            margin-top: 10px;
            width: 280px;
            height: 40px;
        }
    </style>
</head>
<body>
<div align="center">
    <h1>APK文件上传</h1>
    <form method="post" action="/getpatch/doMultiUpload" enctype="multipart/form-data" class="myform">
        <input type="file" name="oldApk" class="myInput"/>
        <br>
        <input type="file" name="newApk" class="myInput"/>
        <br>
        <button type="submit" id="sbutton">上传文件</button>
    </form>

</div>
</body>
</html>
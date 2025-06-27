<%--
  Created by IntelliJ IDEA.
  User: 魏志航
  Date: 2024/11/22
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WebSSO 退出</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;
            display: flex;
            align-items: center; /* 垂直居中 */
            justify-content: center; /* 水平居中 */
            position: relative;
            overflow: hidden; /* 避免背景视频溢出 */
        }

        video {
            position: fixed; /* 使视频固定在视口中 */
            top: 0; /* 视频顶部对齐页面顶部 */
            left: 0; /* 视频左侧对齐页面左侧 */
            width: 100%; /* 视频宽度 100% */
            height: 100%; /* 视频高度 100% */
            object-fit: cover; /* 保持视频纵横比并覆盖整个页面 */
            z-index: -1; /* 背景视频位于所有元素之下 */
        }

        .container {

            z-index: 1; /* 确保内容在视频上方 */
            background: rgba(255, 255, 255, 0.8); /* 白色半透明背景 */
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
        }
    </style>
</head>
<body>
<video autoplay muted loop>
    <source src="static/Wave.mp4" type="video/mp4">  <!-- 替换为您的 MP4 文件路径 -->
    您的浏览器不支持视频标签。
</video>
    <div class="container">
        <h1 style="text-align: center;">用户 <%= request.getAttribute("name") %>, <%= request.getAttribute("order") %>!</h1>
        <div style="text-align: center;"><a href="login">重新登录</a></div>
    </div>
</body>
</html>

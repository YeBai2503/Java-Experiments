<%--
  Created by IntelliJ IDEA.
  User: 魏志航
  Date: 2024/11/15
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>WebSSO</title>
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
      width: 18vw; /* 宽度设置为 40% */
      z-index: 1; /* 确保内容在视频上方 */
      background: rgba(255, 255, 255, 0.8); /* 白色半透明背景 */
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
    }

    .header {
      text-align: center;
    }

    .error {
      color: red;
      font-weight: bold;
      text-align: center;
    }

    .input-wrapper {
      margin-bottom: 15px;
      position: relative;
    }

    .input-wrapper input {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
      box-sizing: border-box;
    }

    .switch-page {
      text-align: center;
    }
    .switch-page button {
      padding: 10px 20px; /* 添加内边距 */
      font-size: 16px; /* 字体大小 */
      color: white; /* 字体颜色 */
      background-color: #4CAF50; /* 按钮背景颜色 */
      border: none; /* 去掉边框 */
      border-radius: 5px; /* 圆角边框 */
      cursor: pointer; /* 鼠标悬停时显示为手型 */
      transition: background-color 0.3s, transform 0.3s; /* 添加过渡效果 */
    }

    .switch-page button:hover {
      background-color: #45a049; /* 悬停时的背景颜色 */
      transform: translateY(-2px); /* 悬停时向上移动 */
    }

    .switch-page button:active {
      transform: translateY(1px); /* 按钮点击时微微下沉 */
    }
  </style>
</head>
<body>
<video autoplay muted loop>
  <source src="static/Wave.mp4" type="video/mp4">  <!-- 替换为您的 MP4 文件路径 -->
  您的浏览器不支持视频标签。
</video>

<div class="container">
  <div class="header">
<%--    <h2>抓蛙大平台，有梦你就来</h2>--%>
    <h1>登录</h1>
  </div>
  <form action="<%= request.getContextPath() %>/login" method="post">
    <div class="input-wrapper">
      账号:<input type="text" id="loginUsername" name="username" placeholder="请输入账号/邮箱" required>
    </div>
    <div class="input-wrapper">
      密码:<input type="password" id="loginPassword" name="password" placeholder="请输入密码" required>
      <i class="fa fa-lock"></i>
      <i class="fa fa-eye-slash toggle-password" id="toggleLoginPassword"></i>
    </div>

    <!-- 隐藏字段，用于存储前一个页面的 URL -->
    <input type="hidden" name="previousUrl" value="<%= request.getAttribute("previousUrl")%>">

    <c:if test="${not empty errorMessage}">
      <p class="error" id="loginError">${errorMessage}</p>
    </c:if>
    <div class="switch-page" style="text-align: center;"> <!-- 新增文本居中 -->
      <button type="submit">登录</button> <!-- 按钮 -->
    </div>


  </form>
  <div class="switch-page">
    <p>没有账户？<a href="register.jsp">点此注册</a></p>
  </div>
</div>

<script>
  document.addEventListener("DOMContentLoaded", function() {
    const usernameInput = document.getElementById("loginUsername");
    const passwordInput = document.getElementById("loginPassword");
    const errorMessageElement = document.getElementById("loginError");

    if (usernameInput !== null && errorMessageElement !== null) {
      usernameInput.addEventListener("input", function() {
        errorMessageElement.textContent = "";  // 清空错误提示
      });
    }

    if (passwordInput !== null && errorMessageElement !== null) {
      passwordInput.addEventListener("input", function() {
        errorMessageElement.textContent = "";  // 清空错误提示
      });
    }
  });
</script>
</body>
</html>
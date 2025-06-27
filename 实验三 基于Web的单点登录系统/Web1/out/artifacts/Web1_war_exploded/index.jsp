<%--
  Created by IntelliJ IDEA.
  User: 魏志航
  Date: 2024/11/15
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.*" %>
<%@ page import="java.util.ArrayList" %>
<% String idValue = request.getAttribute("id").toString(); %>
<html>
  <head>
    <title>Web1</title>
    <style>
      body, html {
        height: 100%;
        margin: 0;
        font-family: Arial, sans-serif;
        display: flex;
        align-items: center; /* 垂直居中 */
        justify-content: center; /* 水平居中 */
        position: relative;
        /*overflow: hidden; !* 避免背景视频溢出 *!*/
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
        width: 40vw; /* 宽度设置为 40% */
        z-index: 1; /* 确保内容在视频上方 */
        background: rgba(255, 255, 255, 0.8); /* 白色半透明背景 */
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
      }
      table {
        width: 100%;
        border-collapse: collapse;
      }
      th, td {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
      }
      th {
        background-color: #f2f2f2;
      }
      /* Web1、Web2按钮样式 */
      button[name="webInfo"] {
        background-color: #007BFF; /* 蓝色背景 */
        color: white; /* 白色文字 */
      }

      button[name="webInfo"]:hover {
        background-color: #0056b3; /* 悬停时更深的蓝色 */
        transform: scale(1.05); /* 悬停时放大一点 */
      }

      /* 退出和注销账户按钮样式 */
      .danger-button {
        background-color: crimson; /* 背景为深红色 */
        color: white; /* 白色文本 */
        padding: 10px 20px; /* 内边距 */
        font-size: 16px; /* 字体大小 */
        border: none; /* 去掉边框 */
        border-radius: 5px; /* 圆角 */
        cursor: pointer; /* 鼠标手型 */
        transition: background-color 0.3s, transform 0.2s; /* 背景颜色和变换的转换效果 */
      }

      .danger-button:hover {
        background-color: darkred; /* 悬停时更深的红色 */
        transform: scale(1.05); /* 悬停时放大一点 */
      }
      button {
        padding: 10px 20px; /* 内边距 */
        font-size: 16px; /* 字体大小 */
        border: none; /* 去掉边框 */
        border-radius: 5px; /* 圆角 */
        cursor: pointer; /* 鼠标手型 */
        transition: background-color 0.3s, transform 0.2s; /* 背景颜色和变换的转换效果 */
      }
    </style>
  </head>
  <body>
  <video autoplay muted loop>
    <source src="static/Waves.mp4" type="video/mp4">  <!-- 替换为您的 MP4 文件路径 -->
    您的浏览器不支持视频标签。
  </video>

  <div class="container">
  <h1>鸣潮大家庭 欢迎, <%= request.getAttribute("name") %></h1>
    <p><strong>用户 ID:</strong> <%= request.getAttribute("id") %></p>
    <p><strong>用户名:</strong> <%= request.getAttribute("name") %></p>
    <p><strong>邮箱:</strong> <%= request.getAttribute("email") %></p>
    <p><strong>管理员身份:</strong> <%= request.getAttribute("manager") %></p>

    <form action="<%= request.getContextPath() %>/Jump" method="post">
      <button type="submit" name="webInfo" value="http://localhost:8082/Web2_war_exploded/index">Web2</button>
    </form>


    <h2>当前在线用户</h2>
    <table>
      <thead>
      <tr>
        <th>用户ID</th>
        <th>昵称</th>
        <th>邮箱</th>
        <th>时间</th>
        <th>网站</th>
      </tr>
      </thead>
      <tbody>
      <%
        OnlineBO onlineBO = (OnlineBO) request.getAttribute("onlineBO");
        ArrayList<User> onlineList = onlineBO.getOnline();
        if (onlineList != null) {
          for (User row : onlineList) {
      %>
      <tr>
        <td><%= row.getId() %></td>
        <td><%= row.getName() %></td>
        <td><%= row.getEmail() %></td>
        <td><%= row.getTime() %></td>
        <td><%= row.getWeb() %></td>
      </tr>
      <%
          }
        }
      %>
      </tbody>
    </table><br><br>

    <form action="<%= request.getContextPath() %>/logout" style="text-align: center;" method="post">
        <button type="submit"  class="danger-button">退出</button>
    </form>

    <form action="<%= request.getContextPath() %>/delete" style="text-align: center;" method="post" onsubmit="return confirmDelete();">
      <button type="submit"  class="danger-button" >注销账户</button>
    </form>
  </div>

  <script>
    var idValue = '<%= request.getAttribute("id") != null ? request.getAttribute("id").toString() : "" %>';

    // 监听网页打开，记录在线状态
    window.addEventListener("load", function () {
      var data = new URLSearchParams();
      data.append('id', idValue); // 将 'id' 值添加到数据中
      // 发送数据到服务器
      navigator.sendBeacon('<%= request.getContextPath() %>/online', data);
    });

    // 监听网页关闭，发送离线状态
    window.addEventListener("beforeunload", function (event) {
      // console.log("oooooooofffffff");
      // 创建要发送的数据，使用 URLSearchParams 格式
      var data = new URLSearchParams();
      data.append('id', idValue); // 将 'id' 值添加到数据中
      // 发送数据到服务器
      navigator.sendBeacon('<%= request.getContextPath() %>/offline', data);
      // 提示用户是否真的要离开
      // var confirmationMessage = "您确定要走吗？";
      // event.returnValue = confirmationMessage; // 兼容性用法
      // return confirmationMessage; // 一些浏览器需要直接返回
    });

    function confirmDelete() {
      return confirm("您确定要注销账户吗？😢😢");
    }
  </script>
  </body>
</html>

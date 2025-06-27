<%--
  Created by IntelliJ IDEA.
  User: 魏志航
  Date: 2024/11/21
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ssoserver.model.User" %>
<%@ page import="com.ssoserver.model.*" %>
<%@ page import="java.util.ArrayList" %>
<!-- 替换为真实包名 -->
<html>
<head>
    <title>WebSSO|账号管理</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;

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
            max-width: 800px;
            margin: 20px auto; /* 居中容器，并上下留有空间 */
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

        .btn-enable {
            color: white;
            background-color: blue;
        }

        .btn-disable {
            color: white;
            background-color: crimson;
        }

        /* 将折叠按钮放到标题的右侧 */
        .collapse-button {
            width: 0; /* 初始宽度为0 */
            height: 0; /* 初始高度为0 */
            border-left: 10px solid transparent; /* 形成三角形的左边 */
            border-right: 10px solid transparent; /* 形成三角形的右边 */
            border-bottom: 10px solid gray; /* 设置按钮的底边为灰色 */
            cursor: pointer; /* 鼠标指针效果 */
            margin-left: 10px; /* 给按钮一点左边距 */
            transition: transform 0.3s; /* 添加平滑过渡效果 */
        }


        /* 初始化时隐藏表格（可选） */
        #feedbackTable {
            display: table; /* 默认显示 */
        }
        button {
            padding: 10px 20px; /* 内边距 */
            font-size: 16px; /* 字体大小 */
            border: none; /* 去掉边框 */
            border-radius: 5px; /* 圆角 */
            cursor: pointer; /* 鼠标手型 */
            transition: background-color 0.3s, transform 0.2s; /* 背景颜色和变换的转换效果 */
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
        /* 小按钮样式 */
        .btn-small {
            padding: 5px 10px; /* 小按钮的内边距 */
            font-size: 14px; /* 小按钮的字体大小 */
        }

        /* 启用状态的样式（蓝色背景） */
        .btn-enable {
            background-color: #007BFF; /* 启用按钮的蓝色背景 */
            color: white; /* 白色文本 */
        }

        .btn-enable:hover {
            background-color: #0056b3; /* 悬停时更深的蓝色 */
            transform: scale(1.05); /* 悬停时稍微放大 */
        }

        /* 禁用状态的样式（红色背景） */
        .btn-disable {
            background-color: #dc3545; /* 禁用按钮的红色背景 */
            color: white; /* 白色文本 */
        }

        .btn-disable:hover {
            background-color: #c82333; /* 悬停时更深的红色 */
            transform: scale(1.05); /* 悬停时稍微放大 */
        }

    </style>
</head>
<body>
<video autoplay muted loop>
    <source src="static/Wave.mp4" type="video/mp4">  <!-- 替换为您的 MP4 文件路径 -->
    您的浏览器不支持视频标签。
</video>
<div class="container">
    <h1 style="text-align: center;">WebSSO 欢迎, <%= request.getAttribute("name") %></h1>
    <h2 style="text-align: center; ">抓蛙大平台，有梦你就来！</h2>

    <form action="<%= request.getContextPath() %>/jump" method="post" style="text-align: center;">
        <button type="submit" name="webInfo" value="http://localhost:8081/Web1_war_exploded/index">Web1</button>
        <button type="submit" name="webInfo" value="http://localhost:8082/Web2_war_exploded/index">Web2</button>
    </form>

    <form action="<%= request.getContextPath() %>/logout" method="post" style="text-align: center;">
        <button type="submit" class="danger-button" >退出</button>
    </form>

    <form action="<%= request.getContextPath() %>/delete" method="post" onsubmit="return confirmDelete();" style="text-align: center;">
        <button type="submit" class="danger-button" >注销账户</button>
    </form>

    <h2 style="display: flex; align-items: center; margin: 0;text-align: center;">
        <span style="flex-grow: 1;margin-right: 0px;">当前在线用户</span>
        <span style="margin-left: 1px;"><div class="collapse-button" id="onlineButton" onclick="toggleTable('onlineTable','onlineButton')" style="border-left: 10px solid transparent; border-right: 10px solid transparent; border-bottom: 10px solid gray;"></div></span>
    </h2><br>
    <table id="onlineTable">
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

    <%
        Integer isManager = (Integer) request.getAttribute("isManager"); // 获取管理员状态
        if (isManager != null && isManager == 1) { // 检查是否是管理员
    %>
        <h2 style="display: flex; align-items: center; margin: 0;text-align: center;">
            <span style="flex-grow: 1;margin-right: 0px;">&lt管理员特权&gt禁用用户</span>
            <span><div class="collapse-button" id="forbidButton" onclick="toggleTable('forbidTable','forbidButton')"></div></span>
        </h2><br>
        <table id="forbidTable">
            <thead>
            <tr>
                <th>用户ID</th>
                <th>昵称</th>
                <th>邮箱</th>
                <th>禁用情况</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <%
                ArrayList<User> forbidList = (ArrayList<User>) request.getAttribute("forbidList");
                if (forbidList != null) {
                    for (User row : forbidList) {
            %>
            <tr>
                <td><%= row.getId() %></td>
                <td><%= row.getName() %></td>
                <td><%= row.getEmail() %></td>
                <td><%= (row.getIsForbid() == 1) ? "是" : "否" %></td>
                <td>
                    <form action="<%= request.getContextPath() %>/forbid" method="post">
                        <input type="hidden" name="forbidId" value="<%= row.getId() %>">
                        <input type="hidden" name="isForbid" value="<%= row.getIsForbid() %>">
                        <button type="submit" class="<%= (row.getIsForbid() == 1) ? "btn-enable btn-small" : "btn-disable btn-small" %>">
                            <%= (row.getIsForbid() == 1) ? "启用" : "禁用" %>
                        </button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    <% } %>
    <br><br>

    <h2 style="display: flex; align-items: center; margin: 0;text-align: center;">
        <span style="flex-grow: 1;margin-right: 0px;">您的登录历史记录</span>
        <span><div class="collapse-button" id="historyButton" onclick="toggleTable('historyTable','historyButton')"></div></span>
    </h2><br>
    <table id="historyTable">
        <thead>
        <tr>
            <th>用户ID</th>
            <th>昵称</th>
            <th>时间</th>
            <th>网站</th>
        </tr>
        </thead>
        <tbody>
        <%
            HistoryBO historyBO = (HistoryBO) request.getAttribute("historyBO");
            ArrayList<User> dataList = historyBO.getHistory();
            if (dataList != null) {
                for (User row : dataList) {
        %>
        <tr>
            <td><%= row.getId() %></td>
            <td><%= row.getName() %></td>
            <td><%= row.getTime() %></td>
            <td><%= row.getWeb() %></td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

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
            var data = new URLSearchParams();
            data.append('id', idValue); // 将 'id' 值添加到数据中
            // 发送数据到服务器
            navigator.sendBeacon('<%= request.getContextPath() %>/offline', data);
        });

        function confirmDelete() {
            return confirm("您确定要注销账户吗？😢😢");
        }

        // 折叠/展开表格功能
        function toggleTable(tableId, buttonId) {
            var table = document.getElementById(tableId);
            var button = document.getElementById(buttonId); // 根据 ID 获取按钮
            var currentDisplay = window.getComputedStyle(table).display;


            // 在这里添加调试信息
            console.log('Table:', table);
            console.log('Button:', button);
            if (currentDisplay === "none") {
                table.style.display = "table"; // 显示表格
                button.style.transform = "rotate(180deg)"; // 指示展开状态
            } else {
                table.style.display = "none"; // 隐藏表格
                button.style.transform = "rotate(0deg)"; // 指示折叠状态
            }
        }

        // 默认表格显示在页面加载时
        document.addEventListener("DOMContentLoaded", function() {
            document.querySelectorAll(".feedback-table").forEach(table => {
                table.style.display = "table"; // 初始化时显示所有表格
            });
            var buttons = document.querySelectorAll('.collapse-button');
            buttons.forEach(button => {
                button.style.transform = "rotate(180deg)"; // 初始状态指示表格已展开
            });
        });
    </script>

</body>
</html>

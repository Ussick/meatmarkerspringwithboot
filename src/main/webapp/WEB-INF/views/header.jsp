<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Photoshoot by FCT</title>
<link href="static/css/style.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="static/scripts/jquery-3.6.0.js"></script>
<script type="text/javascript" src="static/scripts/jquery.poptrox-0.1.js"></script>
</head>
<body>
<div id="header" class="container">
	<div id="logo">
		<h1 style=font-size:30px><a href="#"> Люблю колбасу и мясо </a></h1>
		<p>я - ботан и тоже лентяй (<a href="http://www.freecsstemplates.org"></a></p>
	</div>
	<div id="menu">
		<ul>
			<li class="current_page_item"><a href="enter">Главная</a></li>
			<li><a href="products">Товары</a></li>
			<li><a href="registration">Регистрация</a></li>
			<li><a href="login">Вход</a></li>
			<li><a href="cart">Корзина</a></li>
		</ul>
	</div>
</div>
<!-- end #header -->
<div id="poptrox">
	<!-- start -->
	<ul id="gallery">
		<li><a href="static/images/img01_big.jpg"><img src="static/images/img001.jpg" title="Number one!" alt="" /></a></li>
		<li><a href="static/images/img02_big.jpg"><img src="static/images/img002.jpg" title="Cannot resist :)" alt="" /></a></li>
		<li><a href="static/images/img03_big.jpg"><img src="static/images/img003.jpg" title="Buy immediately!" alt="" /></a></li>
		<li><a href="static/images/img04_big.jpg"><img src="static/images/img004.jpg" title="Yummy!" alt="" /></a></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
	</ul>
	<br class="clear" />
	<script type="text/javascript">
		$('#gallery').poptrox();
		</script>
	<!-- end -->
</div>
<div id="page">
	<div id="bg1">
		<div id="bg2">
			<div id="bg3">
				<div id="content">
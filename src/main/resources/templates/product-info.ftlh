<!DOCTYPE html>
<html>
<head>
    <title>
        Avito
    </title>
</head>
<body>
<h1> BUYSELL </h1> <hr>
<h4> Подробная информация о товаре </h4>
<#list images as img>
    <img src = "/images/${img.id}" height = "60px"/> <br><br>
</#list>
<b> Название товара: </b> ${product.title} <br>
<b> ИНформация о товаре: </b> ${product.description} <br>
<b> Цена: </b> ${product.price} <br>
<b> Город: </b> ${product.city} <br>
<#--<b> Автор: </b> <a href = "user/${product.user.id}">${product.user.name}</a> <br>-->
<b> Автор: </b> <a href = "/user/${product.user.id}">${product.user.name}</a> <br>
<hr>
<h3> Создать новый товра</h3>
<form action = "/product/delete/${product.id}" method = "post" >
    <input type="submit" value = "Удалить товар" />
</form>
</body>
</html>
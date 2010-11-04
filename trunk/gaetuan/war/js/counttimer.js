// JavaScript Document of time counter
function countDown(){
var i;
var objArray =document.getElementsByTagName("label");
//alert(objArray.length);
for (i=0;i<(objArray.length);i++)
{
if (objArray[i].id.indexOf("article_")>-1) 
{
objHid = document.getElementById("hid_article_" + objArray[i].id.substring(objArray[i].id.indexOf("_")+1)); 

//objHid.value就是对应的时间值，然后改下面就行了

var d=Date.parse(objHid.value);

var today=new Date();
var time=d-today;
var 时间=objArray[i];
if(Math.floor(time)<=0){
时间.innerHTML='抢购时间已过，谢谢您的关注!';
}
else
{
var 天=Math.floor(time/(1000*60*60*24));
var 小时=Math.floor(time/(1000*60*60))%24;
var 分=Math.floor(time/(1000*60))%60;
var 秒=Math.floor(time/1000)%60;
时间.innerHTML=天+'    天'+小时+'小时'+分+'分'+秒+'秒';
时间.style.fontSize='16px';
时间.style.color='#333';
} 

}
}
setTimeout('countDown()',1000);
}
countDown();
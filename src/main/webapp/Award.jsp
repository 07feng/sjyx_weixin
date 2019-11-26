<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/7 0007
  Time: 上午 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>华铁股份杯入展名单公布</title>
    <script src="http://code.jquery.com/jquery-2.0.0.min.js"></script>
    <script type="application/javascript" language="JavaScript">
        var model = 1;  //显示模块
        var page1 = 1;
        var page2 = 1;
        var page3 = 1;
        var imgData = null;

        window.onload = function(){
            imgLocation("main1", "box");

            setInterval('temp()',1500);
        };

        function temp() {
            var cparent = document.getElementById("main"+model);
            //把ajax数据加载进页面
            var p = 1;
            var temp = "4";
            var tem = false;
            if(model == 1) {
                temp = "4";
                page1 ++;
                p = page1;
                if (p<=5)
                    tem = true;
            }
            if(model == 2) {
                temp = "5";
                page2 ++;
                p = page2;
                if (p<=10)
                    tem = true;
            }
            if(model == 3) {
                temp = "2";
                page3 ++;
                p = page3;
                if (p<=4)
                    tem = true;
            }

            if (tem) {
                getData("41", "" + p, temp);
                for (var i = 0; i < imgData.data.imglist.length; i++) {
                    var ccontent = document.createElement("div");
                    ccontent.className = "box";
                    cparent.appendChild(ccontent);
                    var boximg = document.createElement("div");
                    boximg.className = "pic";
                    ccontent.appendChild(boximg);
                    var hre = document.createElement("a");
                    hre.className = "box_a";
                    hre.href = "http://i.91sjyx.com/pc/lastpic.jsp?id=" + imgData.data.imglist[i].imgId;
                    ccontent.appendChild(hre);
                    var img = document.createElement("img");
                    img.src = imgData.data.imglist[i].pic;
                    img.style.width = 145 + "px";
                    hre.appendChild(img);
                }
                //把所有图片数据重新定位一次
                imgLocation("main" + model, "box");
            }
        }

        function checkFlag(){
            var cparent = document.getElementById("main"+model);
            var ccontent = getChildElement(cparent, "box");

            //得到最后一张图距顶部的高度，滚动高度，窗口高度
            var lastContentHeight = ccontent[ccontent.length-1].offsetTop;
            var scrollTop = document.body.scrollTop;
            var pageHeight = document.body.clientHeight;
            console.log(lastContentHeight+":"+scrollTop+":"+pageHeight);

            if(lastContentHeight+100 < scrollTop + pageHeight){
                alert("lastContentHeight="+lastContentHeight+"scrollTop="+scrollTop+"pageHeight="+pageHeight);
                return true;
            }
        }

        function getData(matchId,page,code) {
            $.post("/app/getMatchImgList",
                {
                    matchId:matchId,
                    page:page,
                    code:code
                },
                function(jsonResult){
                    imgData = jsonResult;
                });
        }

        function imgLocation(parent, content){
            //将parent下所有的content全部取出
            var cparent = document.getElementById(parent);
            var ccontent = getChildElement(cparent, content);
            //根据当前浏览器窗口的宽度，确定每行图片数并固定，居中
            var imgWidth = ccontent[0].offsetWidth; //offsetWidth = width + padding + border
            var num = 2;
            cparent.style.cssText = "width:"+imgWidth*num+"px;margin:0 auto";
            //alert("pause");
            //设置一个数组，用来承载第一行的图片信息
            var BoxHeightArr = [];
            for(var i=0; i<ccontent.length; i++){
                if(i<num){
                    //第一行的图片的高度记录下来
                    BoxHeightArr[i] = ccontent[i].offsetHeight;
                    //当ajax数据加载后，程序是将所有图片重新定位，所以第一行的图片要清除position:absolute
                    ccontent[i].style.position = "static";
                }else{
                    var minHeight = Math.min.apply(null, BoxHeightArr);
                    var minIndex = getminheightLocation(BoxHeightArr, minHeight);

                    //把图放在第一行图索引值最小的下面
                    ccontent[i].style.position = "absolute";
                    ccontent[i].style.top = minHeight+"px";
                    ccontent[i].style.left = ccontent[minIndex].offsetLeft+"px";

                    //图片放好位置后更新“第一行图片信息的最小高度”，
                    //然后利用for循环重复这个动作到结束
                    BoxHeightArr[minIndex] = BoxHeightArr[minIndex] + ccontent[i].offsetHeight;
                }
            }
            ;}

        //获取第一行图片高度最小的索引值
        function getminheightLocation(BoxHeightArr, minHeight){
            for(var i in BoxHeightArr){
                if(BoxHeightArr[i] == minHeight){
                    return i;
                }
            }
        }

        //获取所有box
        function getChildElement(parent, content){
            contentArr = parent.getElementsByClassName(content);
            return contentArr;
        }
        function tab(val)
        {
            model = val;
            document.getElementById('a'+val).className='';
            document.getElementById('p'+val).className='works-type';
            for(var i=1;i<4;i++){
                if(i!=val){
                    document.getElementById('p'+i).className='works-type1';
                    document.getElementById('a'+i).className='hide';
                }
            }
            imgLocation('main'+val,'box');
        }
    </script>
    <style type="text/css">
        *{
            padding: 0px;
            margin: 0px;
        }

        #main1{
            position: relative;

        }
        #main2{
            position: relative;

        }
        #main3{
            position: relative;

        }
        .box{
            /*      display: inline-block;*/
            padding: 2px 0px 0px 2px;
            float: left;

        }
        .pic{
            padding: 2px;
            border-radius: 5px;
            border:1px solid #ccc;
            box-shadow: 0 0 2px #ccc;
        }

        .pic img{
            width: 145px;
            height: auto;
        }
        .pic span{
            display: none;
        }

        #first{
            width: 300px;
            margin: 0px auto;
        }

        #first div img{
            width: 300px;
            margin: 0px auto;
        }
        #activity{
            margin: 3px auto;
            color: orangered;
            font-size: small;
            text-align: center;
        }
        #des{
            margin: 2px auto;
            color: orangered;
            font-size: small;
            text-align: center;
        }
        #text{
            margin: 10px;
            padding: 10px;
            border: darkseagreen 1px;
            font-size: smaller;
            color: #cccccc;
        }
        .title{
            width:100px;
            height:20px;
            margin:0 auto;
            padding: 3px 20px;
            background-color: red;
            color: white;
            border-radius: 20px;
            line-height:20px;
        }
        .line{
            width:100%;
            height:1px;
            margin-bottom:20px;
            background-color:#ccc;
        }
        .works{
            width:100%;
            height:10px;
            margin-top:20px;
            margin-bottom:20px;
        }
        .works-type{
            float:left;
            width:33.333%;
            text-align:center;
            border-bottom:1px solid red;
        }
        .works-type1{
            float:left;
            width:33.333%;
            text-align:center;
        }
        .hide{ display:none;}
        .match-title{
            width:100%;
            line-height:20px;
            margin-top:20px;
        }
        .line-a{
            width:100%;
        }
        .line-b{
            width:100px;;
            height:2px;
            margin: 0 auto;
            background-color:red;
            text-align:center;
        }
    </style>
</head>
<body>
<div id="first">
    <div><img src="http://image.91sjyx.com/2017-12-07/1512639363471.jpg" id="sec"></div>
    <div class='match-title'>
        <div id="activity">"华铁股份杯"</div>
        <div id="des">全国手机摄影大赛获奖作品欣赏</div>
    </div>


</div>
<div class='line-a'> <span class='line-b'></span></div>
<div id="text">&nbsp;&nbsp;&nbsp;&nbsp;本届大展以“影像中国情，践行中国梦”为主题，面向广大手机摄影爱好者征集纪录-风光、纪录-纪实、艺术三类摄影作品，手机作品要求集中反映中华大地绚丽的自然风光、多彩的民俗风情和火热的社会生活。大展的目的是让广大摄影爱好者分享、学习、展示独特的拍摄视角和精湛的艺术表现，倡导国际“一带一路”战略、记录“中国制造”强音。</div>

<div class='line'></div>
<div class="title">获奖作品公布</div>
<div class='works' id='works'>
    <div id='p1' class="works-type" onclick='tab(1)'>入展作品</div>
    <div id='p2' class="works-type1" onclick='tab(2)'>入围作品</div>
    <div id='p3' class="works-type1" onclick='tab(3)'>人气作品</div>
</div>
<div id='a1' class=''>
    <div id="main1">
        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1516931020713WI3E05"><img src="http://image.91sjyx.com/2018-01-26/201801260942447986581797.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">双子塔日出</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1517816211540FWW5U1"><img src="http://image.91sjyx.com/2018-02-05/1517816211562.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">全家福</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1516199316281M0RB61"><img src="http://image.91sjyx.com/2018-01-17/1516199323035.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">中国梦 我的梦</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1520598887066QH0FFK"><img src="http://image.91sjyx.com/2018-03-09/1520598892640.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">《侗寨晨曦》</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15132614073943UABC2"><img src="http://image.91sjyx.com/2017-12-14/1513261407695.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">情定军港</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15154913186064633D9"><img src="http://image.91sjyx.com/2018-01-09/1515491323420.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">水杉林</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1519716040231XMS58T"><img src="http://image.91sjyx.com/2018-02-27/1519716042417.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">高原风光美</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1515681643513F3IXLX"><img src="http://image.91sjyx.com/2018-01-11/201801112240333596361563.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">落日余辉</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1513764418492VS57WV"><img src="http://image.91sjyx.com/2017-12-20/1513764421170.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">留住夕阳</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1515054964420QWZAXF"><img src="http://image.91sjyx.com/2018-01-04/1515054970164.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">钢花飞舞</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1520477964392X3ZFB2"><img src="http://image.91sjyx.com/2018-03-08/201803081059229851280593.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">记录</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1520472799655FMK1NO"><img src="http://image.91sjyx.com/2018-03-08/1520472800348.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">婺剧小演员</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1519470721863JHJ3P9"><img src="http://image.91sjyx.com/2018-02-24/1519470724437.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">流光溢彩</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1517833407110GIXAPZ"><img src="http://image.91sjyx.com/2018-02-05/1517833414901.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">大地织锦03</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15166723542023XFAAO"><img src="http://image.91sjyx.com/2018-01-23/1516672359855.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">草原轻骑兵_乌兰牧骑</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15192701961632QV4CL"><img src="http://image.91sjyx.com/2018-02-22/201802221129530492988225.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">蒙古风韵</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1515424974105F6D85F"><img src="http://image.91sjyx.com/2018-01-08/1515424978536.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">高铁在延伸</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1519960352654YD7OOU"><img src="http://image.91sjyx.com/2018-03-02/1519960357360.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">天马行空</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15152294038982F7ZRJ"><img src="http://image.91sjyx.com/2018-01-06/1515229404682.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">云开雾散是美景</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1513089449666MMJVZY"><img src="http://image.91sjyx.com/2017-12-12/1513089453009.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">建设高铁</span>
            </div>
        </div>
    </div>
</div>
<div id='a2' class='hide'>
    <div id="main2">
        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1520155034047IUR902"><img src="http://image.91sjyx.com/2018-03-04/1520155038594.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">铁花飞歌映盛世</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1520000644841NN7H93"><img src="http://image.91sjyx.com/2018-03-02/201803022223597791581588.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">炸龙搏斗</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1518516096724PJJOFJ"><img src="http://image.91sjyx.com/2018-02-13/201802131801290919590338.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">喜事</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1516712978677OO4RZT"><img src="http://image.91sjyx.com/2018-01-23/1516712981170.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">古渡晨亊</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1518856738999S8ZV97"><img src="http://image.91sjyx.com/2018-02-17/20180217043854701-3920616761.JPG?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">过中国年</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1516842102514XB0VIL"><img src="http://image.91sjyx.com/2018-01-25/1516842104634.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">算命先生</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15192105169323S408O"><img src="http://image.91sjyx.com/2018-02-21/1519210521450.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">吹糖人</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15138601509715B9PYX"><img src="http://image.91sjyx.com/2017-12-21/1513860156004.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">央迈勇神山</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1516864638736UZUTN8"><img src="http://image.91sjyx.com/2018-01-25/1516864642943.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">向前向上</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15153418277790383IF"><img src="http://image.91sjyx.com/2018-01-08/1515341830280.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">佛道</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1513056188035HWPF9Y"><img src="http://image.91sjyx.com/2017-12-12/1513056193330.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">古韵</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15135997422708QBNMM"><img src="http://image.91sjyx.com/2017-12-18/1513599742180.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">巡书</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1520242168524GSU3BN"><img src="http://image.91sjyx.com/2018-03-05/1520242176544.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">《让爱回家》</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=152035106670822YLHG"><img src="http://image.91sjyx.com/2018-03-06/1520351069360.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">藏寨夕照</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15205619882686F10Z2"><img src="http://image.91sjyx.com/2018-03-09/1520561991522.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">休息区</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15205221471663RY31D"><img src="http://image.91sjyx.com/2018-03-08/1520522151370.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">夜市</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1519542508114HJ9Q5W"><img src="http://image.91sjyx.com/2018-02-25/1519542505018.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">闲</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1519539332836L0VBRA"><img src="http://image.91sjyx.com/2018-02-25/1519539333659.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">晨雾初开</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1517001748002HN9O1F"><img src="http://image.91sjyx.com/2018-01-27/1517001747742.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">路</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1515803969221G0CHXF"><img src="http://image.91sjyx.com/2018-01-13/1515803973308.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">空中的芭蕾</span>
            </div>
        </div>
    </div>
</div>
<div id='a3' class='hide'>
    <div id="main3">
        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=151340473927128KADP"><img src="http://image.91sjyx.com/2017-12-16/1513404742939.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">天上人间</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15147079189127RZHQK"><img src="http://image.91sjyx.com/2017-12-31/1514707915955.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">《夕染梯田》</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15139583015174HFDWB"><img src="http://image.91sjyx.com/201712356/20171222115818216-6049067917.JPG?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">天使之光沐我乡</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=151369385029785VVXF"><img src="http://image.91sjyx.com/2017-12-19/1513693851043.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">车 . 流</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15134242356464MEYYL"><img src="http://image.91sjyx.com/2017-12-16/1513424239443.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">醉美铜仁</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1512750258807N0E4B3"><img src="http://image.91sjyx.com/2017-12-09/1512750260997.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">中国风</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15139368290337F8RV4"><img src="http://image.91sjyx.com/2017-12-22/1513936829626.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">云</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1513218822991VBXXLS"><img src="http://image.91sjyx.com/2017-12-14/1513218824728.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">延伸中崛起</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1513152433638938GT3"><img src="http://image.91sjyx.com/2017-12-13/1513152434827.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">镶嵌在雪山里的碧玉</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1512699550014FKN7HQ"><img src="http://image.91sjyx.com/2017-12-08/201712081019057705884412.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">霞光万丈</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1514188966545QCTXZY"><img src="http://image.91sjyx.com/2017-12-25/1514188973521.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">霞光万丈</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=15134235493187BRO7R"><img src="http://image.91sjyx.com/2017-12-16/1513423557671.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">稀奇</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1514201333687G51EGR"><img src="http://image.91sjyx.com/2017-12-25/201712251928467137450502.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">蜿蜒</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1516721468457DRP1BN"><img src="http://image.91sjyx.com/2018-01-23/20180123113106108-8215096019.JPG?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">团圆回家路</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1512799125186U0PP4K"><img src="http://image.91sjyx.com/201712343/20171209140421119-4366396080.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">双月湾</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1512831145120OEARBM"><img src="http://image.91sjyx.com/2017-12-09/201712092251450625957307.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">时光商店</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1513435730508I6BXDK"><img src="http://image.91sjyx.com/2017-12-16/1513435737339.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">石城的早晨</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1513747450031QRSWZ6"><img src="http://image.91sjyx.com/2017-12-20/1513747456365.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">桥梁之窗</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1512833394419NABKUK"><img src="http://image.91sjyx.com/2017-12-09/1512833393527.jpeg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">暮色月影</span>
            </div>
        </div>

        <div class="box">
            <div class="pic">
                <a href="http://i.91sjyx.com/pc/lastpic.jsp?id=1517840098844RGFJV6"><img src="http://image.91sjyx.com/2018-02-05/1517840099101.jpg?x-oss-process=image/resize,h_300"></a>
                <span class="doc_title">暮色</span>
            </div>
        </div>
    </div>
</div>
</body>
</html>

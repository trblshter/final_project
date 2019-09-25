<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/carousel.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
<main role="main">
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class=""></li>
      <li data-target="#myCarousel" data-slide-to="1" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="2" class=""></li>
    </ol>
    <div class="carousel-inner">
      <div class="carousel-item">
<!--         <svg class="bd-placeholder-img" width="100%" height="100%" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img"><rect width="100%" height="100%" fill="#777"></rect></svg> -->
        <img class="bd-placeholder-img" width="100%" height="100%" alt="" src="image/C.jpg">
        <div class="container">
          <div class="carousel-caption text-left">
            <h1>C.</h1>
            <p>모든 언어의 기초. 절차지향의 대표. 구조적 프로그래밍. 가장 빠른 언어. <br />시스템 프로그램 개발. 응용 프로그램 개발.</p>
<!--             <p><a class="btn btn-lg btn-primary" href="#" role="button">Sign up today</a></p> -->
          </div>
        </div>
      </div>
      <div class="carousel-item active">
<!--         <svg class="bd-placeholder-img" width="100%" height="100%" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img"><rect width="100%" height="100%" fill="#777"></rect></svg> -->
		<img class="bd-placeholder-img" width="100%" height="100%" alt="" src="image/C++.jpg">
        <div class="container">
          <div class="carousel-caption">
            <h1>C++.</h1>
            <p>C의 진화형. C언어에 객체지향을 추가한 언어. Low Level 퍼포먼스를 위해 디자인된 언어. <br />머신(기계)에 아주 가까운 언어. 소프트웨어를 다루는 언어</p>
<!--             <p><a class="btn btn-lg btn-primary" href="#" role="button">Learn more</a></p> -->
          </div>
        </div>
      </div>
      <div class="carousel-item">
<!--         <svg class="bd-placeholder-img" width="100%" height="100%" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img"><rect width="100%" height="100%" fill="#777"></rect></svg> -->
		<img class="bd-placeholder-img" width="100%" height="100%" alt="" src="image/java.jpg">
        <div class="container">
          <div class="carousel-caption text-right">
            <h1>JAVA.</h1>
            <p>객체지향의 대표. 어디서나 실행할 수 있는 언어. 웹 애플리케이션과 잘맞는 언어.<br />어떠한 마이크로프로세서에서 실행이 되는 언어. 인기있는 언어중 하나.</p>
<!--             <p><a class="btn btn-lg btn-primary" href="#" role="button">Browse gallery</a></p> -->
          </div>
        </div>
      </div>
    </div>
    <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>


  <!-- Marketing messaging and featurettes
  ================================================== -->
  <!-- Wrap the rest of the page in another container to center all the content. -->

  <div class="container marketing">

    <!-- Three columns of text below the carousel -->
    <div class="row">
      <div class="col-lg-4">
<!--    		<svg class="bd-placeholder-img rounded-circle" width="140" height="140" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 140x140"><title>Placeholder</title><rect width="100%" height="100%" fill="#777"></rect><text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg> -->
		<img class="bd-placeholder-img rounded-circle" width="140" height="140" alt="" src="image/book.jpg">
        <h2>학생 모집</h2>
        <p>선생님이 학생을 모집하거나 학생이 학생을<br>모집하는 곳입니다. 이곳에서 회원 여러분과 잘 맞는 곳을 찾아 가시길 바랍니다. </p>
        <p><a class="btn btn-secondary" href="lecture/lectureList.do?type=LL&what=ST" role="button">View details »</a></p>
      </div><!-- /.col-lg-4 -->
      <div class="col-lg-4">
<!--         <svg class="bd-placeholder-img rounded-circle" width="140" height="140" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 140x140"><title>Placeholder</title><rect width="100%" height="100%" fill="#777"></rect><text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg> -->
        <img class="bd-placeholder-img rounded-circle" width="140" height="140" alt="" src="image/teacher.png">
        <h2>선생님 모집</h2>
        <p>선생님이 학생들을 모집하는 곳입니다.<br>학생 여러분들은 이곳에 들어가셔서 원하는<br>강의와 선생님을 선택하여 수업을 들으실 수 있습니다.</p>
        <p><a class="btn btn-secondary" href="lecture/lectureList.do?type=LL&what=TE" role="button">View details »</a></p>
      </div><!-- /.col-lg-4 -->
      <div class="col-lg-4">
<!--         <svg class="bd-placeholder-img rounded-circle" width="140" height="140" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 140x140"><title>Placeholder</title><rect width="100%" height="100%" fill="#777"></rect><text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg> -->
		<img class="bd-placeholder-img rounded-circle" width="140" height="140" alt="" src="image/list.png">
        <h2>공지사항</h2>
        <p>공지사항입니다. 회원 여러분들은 이곳에서<br>새로운 기능들을 확인해 주세요!</p>
        <p><a class="btn btn-secondary" href="board/list.do?type=NO" role="button">View details »</a></p>
      </div><!-- /.col-lg-4 -->
    </div><!-- /.row -->
  </div><!-- /.container marketing -->
</main>

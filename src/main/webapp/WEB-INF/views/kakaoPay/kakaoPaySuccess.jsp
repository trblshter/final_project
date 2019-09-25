<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<h1 class="display-4 font-weight-bold mb-4 mt-2"><i class="fas fa-comment mr-2 display-5"></i><span>pay</span></h1>

<h3 class="mb-4">카카오페이 결제가 <br>정상적으로 완료되었습니다.</h3>
<div class="tableWrap">

<table class="table mb-0">
	<tbody>
		<tr>
			<th>회원아이디</th>
			<td>${paymentVO.partner_user_id }</td>
		</tr>
		<tr>
			<th>주문번호</th>
			<td>${paymentVO.partner_order_id }</td>
		</tr>
		<tr>
			<th>결제금액</th>
			<td>${paymentVO.total }원</td>
		</tr>
		<tr>
			<th>결제방법</th>
			<td>${paymentVO.payment_method_type }</td>
		</tr>
	</tbody>
</table>
</div>

<style>
#content {
	text-align: center;
	background: #ffdf3b;
	min-height: 100vh;
	padding: 25px;
}

.far.fa-credit-card {
	font-size: 4rem;
	color: #fff;
	padding: 43px;
	border-radius: 50%;
	border: 10px double #fff;
	margin-bottom: 16px;
}

h1 span {
	position: relative;
    top: -7px;
}

.tableWrap {
	background: #fff;
	border-radius: 6px;
	padding:25px;
}
.tableWrap tr:first-child th,
.tableWrap tr:first-child td{
	border-top:0 none;
}
</style>
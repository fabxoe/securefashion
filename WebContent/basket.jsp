<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="domain.Basket"%>
<%@page import="java.util.ArrayList"%>
<%@page import="domain.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product List</title>
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous">
</script>
<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>


<% ArrayList<Basket> baskets = (ArrayList<Basket>) request.getAttribute("baskets");%>
<% User user = (User) request.getAttribute("user");%>
<% session.setAttribute("user", user);%>
<% session.setAttribute("baskets", baskets); %>
</head>
<body>
<h2>Hello, <%= user.getUsername()%></h2>
        <table border="2px">
            <tr>
                <th width="200">Basket ID</th>
                <th width="200">User Name</th>
                <th width="200">Product ID</th>
                <th width="200">Numbers</th>
                <th width="200">Delete</th>
            </tr>
            <%
                for (int i = 0; i < baskets.size(); i++) {
                    Basket basket = baskets.get(i);
            %> 
            <tr>
                <td align="center"><%=basket.getBasketid()%></td>
                <td align="center"><%=user.getUsername()%></td>
                <td align="center"><%=basket.getProductid()%></td>
                <td align="center"><%=basket.getNumbers()%></td>
                <td align="center"> 
               		<form action="delete" method="post">
                        <input type="hidden" name="basketid" value="<%=basket.getBasketid()%>">
                        <input type="hidden" name="userid" value="<%=user.getUserid()%>">
                        <input type="submit" value="Delete">
                    </form>
               	</td>
            </tr>
            <% }%>
        </table>   
       
        <button id="check_module" type="button">Pay</button>
<script>
$("#check_module").click(function () {
var IMP = window.IMP; // 생략가능
IMP.init('imp47988839');
// 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
// i'mport 관리자 페이지 -> 내정보 -> 가맹점식별코드
IMP.request_pay({
pg: 'inicis', // version 1.1.0부터 지원.
/*
'kakao':카카오페이,
html5_inicis':이니시스(웹표준결제)
'nice':나이스페이
'jtnet':제이티넷
'uplus':LG유플러스
'danal':다날
'payco':페이코
'syrup':시럽페이
'paypal':페이팔
*/
pay_method: 'card',
/*
'samsung':삼성페이,
'card':신용카드,
'trans':실시간계좌이체,
'vbank':가상계좌,
'phone':휴대폰소액결제
*/
merchant_uid: 'merchant_' + new Date().getTime(),
/*
merchant_uid에 경우
https://docs.iamport.kr/implementation/payment
위에 url에 따라가시면 넣을 수 있는 방법이 있습니다.
참고하세요.
나중에 포스팅 해볼게요.
*/
name: '주문명:결제테스트',
//결제창에서 보여질 이름
amount: 1000,
//가격
buyer_email: 'iamport@siot.do',
buyer_name: '구매자이름',
buyer_tel: '010-1234-5678',
buyer_addr: '서울특별시 강남구 삼성동',
buyer_postcode: '123-456',
m_redirect_url: 'http://localhost:8088/kh_academy_FashionSemiProject/paymentlist/payment'//'https://www.yourdomain.com/payments/complete'
/*
모바일 결제시,
결제가 끝나고 랜딩되는 URL을 지정
(카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐)
*/
}, function (rsp) {
console.log(rsp);
if (rsp.success) {
var msg = '결제가 완료되었습니다.';
msg += '고유ID : ' + rsp.imp_uid;
msg += '상점 거래ID : ' + rsp.merchant_uid;
msg += '결제 금액 : ' + rsp.paid_amount;
msg += '카드 승인번호 : ' + rsp.apply_num;
} else {
var msg = '결제에 실패하였습니다.';
msg += '에러내용 : ' + rsp.error_msg;
}
alert(msg);
});
});
</script>
      
</body>
</html>
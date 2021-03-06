<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/style.css">
<link rel="stylesheet" href="./css/settlementConfirm.css">
<title>決済確認</title>

</head>
<body>
	<s:include value="header.jsp" />
	<div id="contents">
		<h1>決済確認画面</h1>

		<!-- 宛先が登録されている場合の動作 -->
		<s:if test="#session.destinationInfoDtoList.size()>0">

			<s:form id="form" action="SettlementCompleteAction">
				<!--宛先情報項目  -->
				<table class="horizontal-list-table">
					<thead>
						<tr>
							<th><s:label value="宛先情報選択" /></th>
							<th><s:label value="姓" /></th>
							<th><s:label value="名" /></th>
							<th><s:label value="ふりがな" /></th>
							<th><s:label value="住所" /></th>
							<th><s:label value="電話番号" /></th>
							<th><s:label value="メールアドレス" /></th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="session.destinationInfoDtoList" status="st">
							<tr>
								<td><s:if test="#st.index == 0">
										<input type="radio" name="id" checked="checked"
											value="<s:property value='id'/>" />
									</s:if>
									<s:else>
										<input type="radio" name="id" value="<s:property value='id'/>" />
									</s:else></td>
								<td><s:property value="familyName" /></td>
								<td><s:property value="firstName" /></td>
								<td><s:property value="familyNameKana" /><span> </span>
								<s:property value="firstNameKana" /><br></td>
								<td><s:property value="userAddress" /></td>
								<td><s:property value="telNumber" /></td>
								<td><s:property value="email" /></td>
						</s:iterator>
					</tbody>
				</table>

				<div class="submit_btn_box">
					<div id=".contents-btn-set">
						<s:submit value="決済" class="submit_btn" />
					</div>
				</div>
			</s:form>
		</s:if>

		<!-- 宛先が登録されていない場合 -->
		<s:else>
			<div class="message message_nomal">宛先情報がありません。</div>
		</s:else>
		<div class="submit_btn_box">
			<div id=".contents-btn-set">
				<s:form action="CreateDestinationAction">
					<s:submit value="新規宛先登録" class="submit_btn" />
				</s:form>
			</div>
		</div>


	</div>
	<s:include value="footer.jsp" />
</body>
</html>
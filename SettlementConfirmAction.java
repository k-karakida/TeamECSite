package com.internousdev.anemone.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.anemone.dao.DestinationInfoDAO;
import com.internousdev.anemone.dto.CartInfoDTO;
import com.internousdev.anemone.dto.DestinationInfoDTO;
import com.internousdev.anemone.dto.PurchaseHistoryInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementConfirmAction extends ActionSupport implements SessionAware{

	private Map<String, Object>session;


	public String execute(){

		//sessionのタイムアウト時の処理
		if(!session.containsKey("mCategoryList")) {
			return "sessionError";
		}
		//宛先登録情報の呼び出し
		if(session.containsKey("loginId")){
			DestinationInfoDAO destinationInfoDao = new DestinationInfoDAO();
			List<DestinationInfoDTO> destinationInfoDtoList = new ArrayList<>();

			destinationInfoDtoList = destinationInfoDao.getDestinationInfo(String.valueOf(session.get("loginId")));
			Iterator<DestinationInfoDTO> iterator = destinationInfoDtoList.iterator();
			if(!(iterator.hasNext())){
				destinationInfoDtoList = null;
			}
			session.put("destinationInfoDtoList", destinationInfoDtoList);
		}

		//カート情報を購入履歴情報へ
			List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDtoList = new ArrayList<PurchaseHistoryInfoDTO>();

			@SuppressWarnings("unchecked")
			List<CartInfoDTO> cartInfoDtoList = (List<CartInfoDTO>)session.get("cartInfoDTOList");

			for(CartInfoDTO dto:cartInfoDtoList){
				PurchaseHistoryInfoDTO purchaseHistoryInfoDTO = new PurchaseHistoryInfoDTO();
				purchaseHistoryInfoDTO.setUserId(String.valueOf(session.get("loginId")));
				purchaseHistoryInfoDTO.setProductId(dto.getProductId());
				purchaseHistoryInfoDTO.setProductName(dto.getProductName());
				purchaseHistoryInfoDTO.setProductNameKana(dto.getProductNameKana());
				purchaseHistoryInfoDTO.setImageFilePath(dto.getImageFilePath());
				purchaseHistoryInfoDTO.setImageFileName(dto.getImageFileName());
				purchaseHistoryInfoDTO.setPrice(dto.getPrice());
				purchaseHistoryInfoDTO.setReleaseCompany(dto.getReleaseCompany());
				purchaseHistoryInfoDTO.setReleaseDate(dto.getReleaseDate());
				purchaseHistoryInfoDTO.setProductCount(dto.getProductCount());
				purchaseHistoryInfoDTO.setTotalPrice(dto.getSubtotal());
				purchaseHistoryInfoDtoList.add(purchaseHistoryInfoDTO);
			}
			session.put("purchaseHistoryInfoDtoList", purchaseHistoryInfoDtoList);

		//ログイン状態かを調べ、未ログインの場合は、ログイン画面に遷移
	    if(!session.containsKey("loginId")){
			session.put("cartFlg", true);
	    	return "login";

		}else{
			return SUCCESS;
		}
	}

	public Map<String, Object> getSession(){
		return session;
	}

	public void setSession(Map<String, Object>session){
		this.session = session;
	}

}

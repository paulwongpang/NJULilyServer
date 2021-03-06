package data.accountinitdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import common.Common;
import common.DefineList;
import common.ParseXML;
import po.AccountaInitPO;
import dataenum.ResultMessage;
import dataservice.accountinitdataservice.AccountInitDataService;

/**
 * @author cylong
 * @version 2014年12月2日 下午8:37:30
 */
public class AccountInitData extends UnicastRemoteObject implements AccountInitDataService {

	/** serialVersionUID */
	private static final long serialVersionUID = -5807537592466570234L;

	protected DefineList<AccountaInitPO> initList;
	/** 保存文件的路径 */
	protected String filePath;
	/** ID前缀 */
	protected String prefix;
	/** 当前最大ID */
	protected int maxID;
	/** 添加时返回的临时ID */
	private String currentID;
	/** ID最大位数 */
	protected int IDMaxBit;
	/** 解析xml文件 */
	protected ParseXML parsexml;

	public AccountInitData() throws RemoteException {
		init();
	}

	/**
	 * @see dataservice.DataService#init()
	 */
	@Override
	public void init() throws RemoteException {
		parsexml = new ParseXML(NAME);
		filePath = parsexml.getValue("path");
		prefix = parsexml.getValue("prefix");
		maxID = Integer.parseInt(parsexml.getValue("maxID"));
		IDMaxBit = Integer.parseInt(parsexml.getValue("IDMaxBit"));
		initList = new DefineList<AccountaInitPO>(filePath);
	}

	/**
	 * @see dataservice.DataService#getID()
	 */
	@Override
	public String getID() throws RemoteException {
		if (initList.isEmpty()) {
			maxID = 0;	// 初始化最大ID
			parsexml.setValue("maxID", Common.intToString(maxID, IDMaxBit));
		}
		currentID = Common.intToString((maxID + 1), IDMaxBit);
		return currentID;
	}

	private void addID() {
		maxID++;
		parsexml.setValue("maxID", currentID);
	}

	/**
	 * @see dataservice.DataService#find(java.lang.String)
	 */
	@Override
	public AccountaInitPO find(String ID) throws RemoteException {
		for(int i = 0; i < initList.size(); i++) {
			if (initList.get(i).getID().equals(ID)) {
				return initList.get(i);
			}
		}
		return null;
	}

	/**
	 * @see dataservice.accountinitdataservice.AccountInitDataService#insert(po.AccountaInitPO)
	 */
	@Override
	public ResultMessage insert(AccountaInitPO po) throws RemoteException {
		for(AccountaInitPO temp : initList.getInList()) {
			if (temp.getID().equals(po.getID())) {
				return ResultMessage.FAILURE;
			}
		}
		initList.add(po);
		addID();
		return ResultMessage.SUCCESS;
	}

	/**
	 * @see dataservice.accountinitdataservice.AccountInitDataService#show()
	 */
	@Override
	public ArrayList<AccountaInitPO> show() throws RemoteException {
		return initList.getInList();
	}

}

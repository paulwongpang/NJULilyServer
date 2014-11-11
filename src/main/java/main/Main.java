package main;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import data.DataFactory;

/**
 * @author cylong
 * @version Nov 2, 2014 3:17:43 PM
 */
public class Main {

	public static void main(String[] args) {
		try {
			// 创建一个远程对象 
			DataFactory factory = new DataFactory();
			// 本地主机上的远程对象注册表Registry的实例，并指定端口为1994，这一步必不可少（Java默认端口是1099），必不可缺的一步，缺少注册表创建，则无法绑定对象到远程注册表上 
			LocateRegistry.createRegistry(1994);
			// 把远程对象注册到RMI注册服务器上，并命名为MyHello 
			// 绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的） 
			Naming.bind("rmi://127.0.0.1:1994/DataFactory", factory);
			// Naming.bind("//localhost:8888/DataFactory",factory); 
			System.out.println(">>>>>INFO:远程服务器启动！");
		} catch (RemoteException e) {
			System.out.println("创建远程对象发生异常！");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("发生URL畸形异常！");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			System.out.println("发生重复绑定对象异常！");
			e.printStackTrace();
		}
	}

}

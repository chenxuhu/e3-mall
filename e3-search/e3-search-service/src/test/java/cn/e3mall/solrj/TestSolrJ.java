package cn.e3mall.solrj;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;

public class TestSolrJ {

	@Test
	public void addDocument() throws Exception{
	//创建一个solrService对象，创建一个连接，参数solr服务的url
	SolrServer sorlService=new HttpSolrServer("http://192.168.25.131:8080/solr");
	//创建一个文档对象solrInputDocument
	SolrInputDocument document=new SolrInputDocument();
	//向文档对象中添加域，文档中必须包含一个id域，所有的域的名称比逊在schema.xml中定义
	document.addField("id", "doc02");
	document.addField("item_title", "测试商品");
	document.addField("item_price", 1000);
	//版文档写入索引库
	sorlService.add(document);
	//提交
	sorlService.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception{
	//创建一个solrService对象，创建一个连接，参数solr服务的url
	SolrServer sorlService=new HttpSolrServer("http://192.168.25.131:8080/solr");
	//删除1
	sorlService.deleteById("doc01");
	//删除2
	//sorlService.deleteByQuery("idd:doc01");
	sorlService.commit();
	}
	
	@Test
	public void queryIndex() throws Exception{
		//创建一个solrService对象，创建一个连接，参数solr服务的url
		SolrServer sorlService=new HttpSolrServer("http://192.168.25.131:8080/solr");
		//创建一个SolrQuery对象
		SolrQuery query=new SolrQuery();
		//设置查询条件
		query.set("q", "*:*");
		query.set("q", "*:*");
		//执行查询，QueryRespose
		QueryResponse queryResponse=sorlService.query(query);
		//取文档拉列表，查询结果的总记录数
		SolrDocumentList documentList=queryResponse.getResults();
		System.out.println("查询结果总记录数："+documentList.getNumFound());
		//遍历文档列表，从取域的内容
		//遍历文档列表，从取域的内容。
				for (SolrDocument solrDocument : documentList) {
					System.out.println(solrDocument.get("id"));
					System.out.println(solrDocument.get("item_title"));
					System.out.println(solrDocument.get("item_sell_point"));
					System.out.println(solrDocument.get("item_price"));
					System.out.println(solrDocument.get("item_image"));
					System.out.println(solrDocument.get("item_category_name"));
				}
	}
	
	
	@Test
	public void queryIndexFuza() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//创建一个查询对象
		SolrQuery query = new SolrQuery();
		//查询条件
		query.setQuery("手机");
		query.setStart(0);
		query.setRows(20);
		query.set("df", "item_title");
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//执行查询
		QueryResponse queryResponse = solrServer.query(query);
		//取文档列表。取查询结果的总记录数
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
		//遍历文档列表，从取域的内容。
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			//取高亮显示
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list !=null && list.size() > 0 ) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}
	
}

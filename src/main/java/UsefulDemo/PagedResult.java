package UsefulDemo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 带有分页信息的列表
 * pageInfo与totalNum不为空时，自动计算totalPage。
 * @param <T>
 */
public class PagedResult<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6726246716422199766L;
	//分页信息
	private PageInfo pageInfo;
	//对象列表
	private List<T> list;
	public Long totalNum;
	public Long totalPage;
	private Map<String,Long> facet;
	
	
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}
	public Long getTotalPage() {
		if(this.totalPage == null && this.totalNum != null && this.pageInfo != null && this.pageInfo.getPageSize()!= null){
			totalPage = (long)Math.ceil( (double)totalNum/(double)this.pageInfo.getPageSize());
		}
		return totalPage;
	}
	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}
	public Map<String,Long> getFacet() {
		return facet;
	}
	public void setFacet(Map<String,Long> facet) {
		this.facet = facet;
	}
}

package UsefulDemo;

import java.io.Serializable;

/**
 * 分页信息的封装
 * 限制每页最多200条记录，页码必须大于0
 */
public class PageInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8492703703842499256L;
	public Long pageNum;
	public Long pageSize;
	public String order;
	public Long startNum;
	public Long maxid;
	public Long minid;
	public Integer searchType;
	public Integer totalNum;

	public Long getMaxid() {
		return maxid;
	}
	public void setMaxid(Long maxid) {
		this.maxid = maxid;
	}
	public Long getMinid() {
		return minid;
	}
	public void setMinid(Long minid) {
		this.minid = minid;
	}
	public Integer getSearchType() {
		return searchType;
	}
	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}
	public Long getPageNum() {
		return pageNum;
	}
	public void setPageNum(Long pageNum) {
		if(pageNum !=null && pageNum.compareTo(0L)<0){
			pageNum = 0L;
		}
		this.pageNum = pageNum;
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		if(pageSize!=null && pageSize.compareTo(200L) >0 ){
			pageSize = 200L;
		}
		this.pageSize = pageSize;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	public Long getStartNum(){
		Long retVal = 0L;
		if(pageNum != null && pageSize != null){
			retVal = (pageNum-1) * pageSize;
		}
		if(retVal < 0){
			retVal =  new Long(0);
		}
		return retVal;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
}

/**
 * 
 */
package cn.sx.decentworld.stateMachine.param;

import cn.sx.decentworld.stateMachine.StateConfig.QueryWealthResult;

/**
 * @ClassName: HandlerParam
 * @Description: 查询wealth返回结果后，调用handler(Main Thread)时传递的参数
 * @author: Jackchen
 * @date: 2016年4月28日 下午3:37:35
 */
public class HandlerParam
{
    private static final String TAG = "HandlerParam";
    private Object object;
    private QueryWealthResult queryWealthResult;

    /**
     * @param object
     * @param queryWealthResult
     */
    public HandlerParam(Object object, QueryWealthResult queryWealthResult)
    {
        super();
        this.object = object;
        this.queryWealthResult = queryWealthResult;
    }

    /**
     * @return the object
     */
    public Object getObject()
    {
        return object;
    }

    /**
     * @param object
     *            the object to set
     */
    public void setObject(Object object)
    {
        this.object = object;
    }

    /**
     * @return the queryWealthResult
     */
    public QueryWealthResult getQueryWealthResult()
    {
        return queryWealthResult;
    }

    /**
     * @param queryWealthResult
     *            the queryWealthResult to set
     */
    public void setQueryWealthResult(QueryWealthResult queryWealthResult)
    {
        this.queryWealthResult = queryWealthResult;
    }

    @Override
    public String toString()
    {
        return "HandlerParam [object=" + object + ", queryWealthResult=" + queryWealthResult + "]";
    }

}

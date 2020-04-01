package com.yx.common.mysql;

import tk.mybatis.mapper.common.*;

/**
 * @Author jesse
 * @Date 4/1/20 8:56 下午
 * @warning 特别注意，该接口不能被扫描到，否则会出错
 **/
public interface BasicMapper<T> extends BaseMapper<T>,
        MySqlMapper<T>, IdsMapper<T>, ConditionMapper<T>, ExampleMapper<T>, RowBoundsMapper<T>, Marker {


    //RowBoundsMapper 主要是配合分页插件PageHelper来实现分页查询的
    //Marker只是一个标记接口

//    ==================BaseMapper提供如下方法==================
//    /**
//     * 保存一个实体，null属性也会保存
//     *
//     * @param record
//     * @return
//     */
//    int insert(T record);
//
//    /**
//     * 保存一个实体，null属性不会保存
//     *
//     * @param record
//     * @return
//     */
//    int insertSelective(T record);
//
//    /**
//     * 根据实体属性作为条件进行删除，查询条件使用等号
//     */
//    int delete(T record);
//
//    /**
//     * 根据主键更新属性不为null的值
//     */
//    int updateByPrimaryKeySelective(T record);
//
//    /**
//     * 根据实体中的属性值进行查询，查询条件使用等号
//     */
//    List<T> select(T record);
//
//    /**
//     * 查询全部结果，select(null)方法能达到同样的效果
//     */
//    List<T> selectAll();
//
//    /**
//     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
//     */
//    T selectOne(T record);
//
//    /**
//     * 根据实体中的属性查询总数，查询条件使用等号
//     */
//    int selectCount(T record);


    //    ==================MySqlMapper提供如下方法==================
//    /**
//     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
//     */
//    public int insertList(List<T> recordList);
//
//    /**
//     * 插入数据，限制为实体包含`id`属性并且必须为自增列，实体配置的主键策略无效
//     */
//    public int insertUseGeneratedKeys(T record);

    //    ==================IdsMapper提供如下方法==================
//    /**
//     * 根据主键@Id进行查询，多个Id以逗号,分割
//     * @param id
//     * @return
//     */
//    List<T> selectByIds(String ids);
//
//    /**
//     * 根据主键@Id进行删除，多个Id以逗号,分割
//     * @param id
//     * @return
//     */
//    int deleteByIds(String ids);


    //    ==================ConditionMapper提供如下方法==================
//    /**
//     * 根据Condition条件进行查询
//     */
//    public List<T> selectByCondition(Object condition);
//
//    /**
//     * 根据Condition条件进行查询
//     */
//    public int selectCountByCondition(Object condition);
//
//    /**
//     * 根据Condition条件删除数据，返回删除的条数
//     */
//    public int deleteByCondition(Object condition);
//
//    /**
//     * 根据Condition条件更新实体`record`包含的全部属性，null值会被更新，返回更新的条数
//     */
//    public int updateByCondition(T record, Object condition);
//
//    /**
//     * 根据Condition条件更新实体`record`包含的全部属性，null值会被更新，返回更新的条数
//     */
//    public int updateByConditionSelective(T record, Object condition);


    //    ==================ExampleMapper提供如下方法==================
//    /**
//     * 根据Example条件进行查询
//     */
//    public List<T> selectByExample(Object example);
//
//    /**
//     * 根据Example条件进行查询，若有多条数据则抛出异常
//     */
//    public T selectOneByExample(Object example);
//
//    /**
//     * 根据Example条件进行查询总数
//     */
//    public int selectCountByExample(Object example);
//
//    /**
//     * 根据Example条件删除数据，返回删除的条数
//     */
//    public int deleteByExample(Object example);
//
//    /**
//     * 根据Example条件更新实体`record`包含的全部属性，null值会被更新，返回更新的条数
//     */
//    public int updateByExample(T record, Object example);
//
//    /**
//     * 根据Example条件更新实体`record`包含的不是null的属性值，返回更新的条数
//     */
//    public int updateByExampleSelective(T record, Object example);

}

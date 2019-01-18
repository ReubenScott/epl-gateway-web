package com.kindustry.system.service;

//import com.baomidou.mybatisplus.service.IService;

/**
 *
 * UserRole 表数据服务层接口
 *
 */
public interface IUserRoleService /*extends IService<UserRole>*/ {

	/**
	 * <p>
	 * 判断是否存在角色对应的用户
	 * </p>
	 * 
	 * @param roleId
	 *            角色ID
	 * @return
	 */
	boolean existRoleUser(Long roleId);

}
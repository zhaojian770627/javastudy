package com.zj.study.framework.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.zj.study.framework.mybatis.entity.TUser;
import com.zj.study.framework.mybatis.mapper.TUserMapper;

public class AssociationQueryTest {
	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() throws IOException {

		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 1.读取mybatis配置文件创SqlSessionFactory
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Test
	// 1对1两种关联方式
	public void testOneToOne() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.执行查询语句并返回结果
		// ----------------------
//		List<TUser> list1 = mapper.selectUserPosition1();
//		for (TUser tUser : list1) {
//			System.out.println(tUser);
//		}

		List<TUser> list2 = mapper.selectUserPosition2();
		for (TUser tUser : list2) {
			System.out.println(tUser.getPosition());
		}
	}
}
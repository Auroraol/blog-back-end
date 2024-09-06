package com.lfj.blog;

import com.alibaba.fastjson2.JSON;
import com.lfj.blog.utils.DateIntervalUtils;
import com.lfj.blog.utils.FilesUtils;
import com.lfj.blog.utils.buildTreeUtil.BuildTreeUtil;
import com.lfj.blog.utils.buildTreeUtil.TreeVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class TemplateApplicationTests {

	@Autowired
	private StringEncryptor stringEncryptor;

	// 将 Person 类型的列表转换为 TreeVo<Person> 类型的列表的示例方法
	private static List<TreeVo<Person>> convertToTreeNodes(List<Person> personList) {
		List<TreeVo<Person>> treeNodes = new ArrayList<>();
		for (Person person : personList) {
			TreeVo<Person> treeNode = new TreeVo<>();
			treeNode.setId(String.valueOf(person.getId()));
			treeNode.setParentId(String.valueOf(person.getParentId()));
			treeNode.setText(person.getName());
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}


	@Test
	void matestin() {
		// 当天
		LocalDate now = LocalDate.now();

		// 获取周一日期
		LocalDate monday = now.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

		// 近两周
		// 获取上周一日期
		LocalDate lastMonday = now.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusDays(7);

		// 本月
		// 获取本月一号日期
		LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());

		// 近三月
		// 获取前两个月的第一天
		LocalDate firstDayOfTwoMonthsAgo = now.minusMonths(2).with(TemporalAdjusters.firstDayOfMonth());


		// 创建日期集合
		List<LocalDate> dateRange = new ArrayList<>();

		// 从上一个周一到当前日期（包括这两天）填充日期集合
		LocalDate currentDate = firstDayOfMonth;
		while (!currentDate.isAfter(now)) {
			dateRange.add(currentDate);
			currentDate = currentDate.plusDays(1);
		}

		System.out.println(dateRange);
	}

	@Test
	void test() {

		ArrayList<EquipmentGrowthTrendDTO> equipmentGrowthTrendDTOS = new ArrayList<>();

		// 创建测试数据
		EquipmentGrowthTrendDTO dto1 = new EquipmentGrowthTrendDTO();
		dto1.setBusinessType(1);
		dto1.setDay("2024-08-01");
		dto1.setNewDevicesCount(10);

		EquipmentGrowthTrendDTO dto2 = new EquipmentGrowthTrendDTO();
		dto2.setBusinessType(2);
		dto2.setDay("2024-08-02");
		dto2.setNewDevicesCount(15);

		EquipmentGrowthTrendDTO dto3 = new EquipmentGrowthTrendDTO();
		dto3.setBusinessType(1);
		dto3.setDay("2024-08-07");
		dto3.setNewDevicesCount(20);

		EquipmentGrowthTrendDTO dto4 = new EquipmentGrowthTrendDTO();
		dto4.setBusinessType(1);
		dto4.setDay("2024-08-010");
		dto4.setNewDevicesCount(20);

		EquipmentGrowthTrendDTO dto5 = new EquipmentGrowthTrendDTO();
		dto5.setBusinessType(1);
		dto5.setDay("2024-07-29");
		dto5.setNewDevicesCount(20);


		// 将测试数据添加到列表中
		equipmentGrowthTrendDTOS.add(dto1);
		equipmentGrowthTrendDTOS.add(dto2);
		equipmentGrowthTrendDTOS.add(dto3);
		equipmentGrowthTrendDTOS.add(dto4);
		equipmentGrowthTrendDTOS.add(dto5);


		Map<Integer, List<EquipmentGrowthTrendStatsDTO>> groupedResult = new LinkedHashMap<>();
//		List<String> nearlyMonthDates = DateIntervalUtils.getNearlyMonthDates();
		List<String> nearlyWeekDates = DateIntervalUtils.getNearlyWeekDates();

		Set<Integer> businessTypes = new HashSet<>();

		// 为每个业务类型创建一个空的EquipmentGrowthTrendStatsDTO列表
		for (EquipmentGrowthTrendDTO dto : equipmentGrowthTrendDTOS) {
			businessTypes.add(dto.getBusinessType());
		}
		for (Integer businessType : businessTypes) {
			groupedResult.put(businessType, new ArrayList<>());
		}

		// 为每个日期和每个业务类型创建EquipmentGrowthTrendStatsDTO对象
		for (String date : nearlyWeekDates) {
			for (Integer businessType : businessTypes) {
				EquipmentGrowthTrendStatsDTO stats = new EquipmentGrowthTrendStatsDTO();
				stats.setDay(date);
				stats.setNewDevicesCount(0); // 初始化新设备计数为0

				// 添加到分组结果Map中
				groupedResult.get(businessType).add(stats);
			}
		}

		// 遍历equipmentGrowthTrendDTOS，更新对应的新设备计数
		for (EquipmentGrowthTrendDTO dto : equipmentGrowthTrendDTOS) {
			for (Integer businessType : businessTypes) {
				if (dto.getBusinessType().equals(businessType)) {
					for (EquipmentGrowthTrendStatsDTO stats : groupedResult.get(businessType)) {
						if (stats.getDay().equals(dto.getDay())) {
							stats.setNewDevicesCount(dto.getNewDevicesCount()); // 更新新设备计数
							break;
						}
					}
				}
			}
		}

		System.out.println(groupedResult);
	}

	@Test
	void contextLoads() {
		String t = "pc:";
		String n = "123456";
		String encodedt = new String(Base64.encodeBase64(t.getBytes()));
		String encodedn = new String(Base64.encodeBase64(n.getBytes()));
		System.out.println(encodedt);
		System.out.println(encodedn);
	}

	@Test
	void encryptTest() {
		String content = "LTAI5tRn3SLwXcQWqcTNHr4r";
		String encryptStr = stringEncryptor.encrypt(content);
		String decryptStr = stringEncryptor.decrypt(encryptStr);
		System.out.println("加密后的内容：" + encryptStr);
		System.out.println("解密后的内容：" + decryptStr);
	}

	// 创建 Person 类型的列表的示例方法
	private List<Person> createPersonList() {


		Person p1 = new Person(1, 0, "顶级父1");
		Person p3 = new Person(3, 1, "子1_3");
		Person p4 = new Person(4, 1, "子1_4");

		Person p21 = new Person(21, 3, "子1_3_21");
		Person p22 = new Person(22, 4, "子1_4_22");
		Person p23 = new Person(23, 4, "子1_4_23");
		Person p24 = new Person(24, 23, "子1_4_23_24");

		Person p5 = new Person(5, 1, "子1_5");
		Person p6 = new Person(6, 1, "子1_6");
		Person p7 = new Person(7, 1, "子1_7");

		Person p2 = new Person(2, 0, "顶级父2");
		Person p8 = new Person(8, 2, "子2_8");

		Person p14 = new Person(14, 8, "子2_8_14");
		Person p15 = new Person(15, 8, "子2_8_15");
		Person p17 = new Person(17, 8, "子2_8_17");

		Person p18 = new Person(18, 14, "子2_8_14_18");
		Person p19 = new Person(19, 14, "子2_8_14_19");
		Person p20 = new Person(20, 15, "子2_8_15_20");

		Person p9 = new Person(9, 2, "子2_9");
		Person p10 = new Person(10, 2, "子2_10");
		Person p11 = new Person(11, 2, "子2_11");
		Person p12 = new Person(12, 2, "子2_12");
		Person p13 = new Person(13, 2, "子2_13");

		Person p16 = new Person(16, 0, "顶级父16");

		List<Person> list = new ArrayList<Person>();

		//打乱排序
		list.add(p15);
		list.add(p14);
		list.add(p8);

		list.add(p23);

		list.add(p11);
		list.add(p12);
		list.add(p5);
		list.add(p13);
		list.add(p18);
		list.add(p19);

		list.add(p24);

		list.add(p10);
		list.add(p17);

		list.add(p7);
		list.add(p9);
		list.add(p21);
		list.add(p4);
		list.add(p16);
		list.add(p6);

		list.add(p20);
		list.add(p22);
		list.add(p1);
		list.add(p2);
		list.add(p3);

		return list.stream().distinct().sorted(Comparator.comparingInt(Person::getId)).collect(Collectors.toList());
	}

	@Test
	void BuildTreeUtil() {


		// 创建 Person 类型的列表，并填充数据
		List<Person> personList = createPersonList();

		// 将 Person 类型的列表转换为 TreeVo<Person> 类型的列表

		List<TreeVo<Person>> treeNodes = convertToTreeNodes(personList);

		// 使用 BuildTreeUtil 的 build 方法构建树形结构
		List<TreeVo<Person>> treeVos = BuildTreeUtil.buildList(treeNodes, "0");


		// 可以进一步处理树形结构，例如遍历树节点等操作
		// 遍历示例
		System.out.println(
				JSON.toJSONString(treeVos)
		);

	}

	@Test
	void test22() throws IOException {
//
//		String inputImagePath = "D:\\PCTMoveData\\Desktop\\1.png"; // 替换为你的输入图像路径
//		String outputBMPPath = "D:\\PCTMoveData\\Desktop\\image.bmp"; // 替换为你的输出 BMP 文件路径
//
//		BMP bmpConverter = new BMP(inputImagePath, outputBMPPath);
//
//		System.out.println("转换完成，BMP 文件已保存到: " + outputBMPPath);

//		 创建一个模拟的 MultipartFile 对象
		MultipartFile multipartFile = mock(MultipartFile.class);

		// 设置文件名
		String originalFilename = "testfile.txt";
		when(multipartFile.getOriginalFilename()).thenReturn(originalFilename);

		// 模拟文件的内容
		byte[] fileContent = "This is a test file".getBytes();
		when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));


		// 调用 saveUploadedFile 方法
		String s = FilesUtils.saveMultipartFile(multipartFile);
		System.out.println(s);
	}

	@Data
	public static class TestDTO {
		private LocalDate date;
		private Integer count;
	}

	@Data
	class EquipmentGrowthTrendDTO {
		Integer businessType;
		String day;
		Integer newDevicesCount;

	}

	@Data
	class EquipmentGrowthTrendStatsDTO {
		String day;
		Integer newDevicesCount;

	}

	@Data
	@AllArgsConstructor
	class Person {
		private Integer id;
		private Integer parentId;

		private String name;

	}

}
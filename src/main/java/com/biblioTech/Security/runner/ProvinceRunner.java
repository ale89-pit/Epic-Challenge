package com.biblioTech.Security.runner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.biblioTech.Security.entity.Municipality;
import com.biblioTech.Security.entity.Province;
import com.biblioTech.Security.repository.MunicipalityRepository;
import com.biblioTech.Security.repository.ProvinceRepository;

@Component
@Order(2)
public class ProvinceRunner implements ApplicationRunner {
	@Autowired
	ProvinceRepository provinceDAO;
	@Autowired
	MunicipalityRepository municipalityDAO;

	String currentWorkingDir = System.getProperty("user.dir");

	@Override
	public void run(ApplicationArguments args) throws Exception {

		if (provinceDAO.findAll().isEmpty()) {
			List<List<String>> recordsProvince = new ArrayList<>();

			BufferedReader brProvince = new BufferedReader(new FileReader(
					currentWorkingDir + "/src/main/resources/province-italiane.csv", StandardCharsets.UTF_8));
			String lineProvince;
			while ((lineProvince = brProvince.readLine()) != null) {
				String[] values = lineProvince.split(";");
				recordsProvince.add(Arrays.asList(values));
				Province p = new Province();
				p.setSign(values[0]);
				p.setName(values[1]);
				p.setRegion(values[2]);
//	     System.out.println(p);
				provinceDAO.save(p);
				// log.info(values[0].toString() + " " + values[1].toString());
//	      records.forEach(el -> log.info(el.toString()));
			}
		}

		if (municipalityDAO.findAll().isEmpty()) {
			List<List<String>> records = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(
					currentWorkingDir + "/src/main/resources/comuni-italiani.csv", StandardCharsets.UTF_8));
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(";");
				Municipality m = new Municipality();
				m.setName(values[2]);
				m.setProvince(provinceDAO.findByName(values[3]));
				m.setProvince_id(values[0]);
				m.setMunicipality_id(values[1]);
				municipalityDAO.save(m);
				records.add(Arrays.asList(values));
//	    System.out.println(records);
//	   // log.info(values[0].toString() + " " + values[1].toString() + " "
//	   //         + values[2].toString() + " ");		   
			}
		}

	}

}
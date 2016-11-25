package com.excilys.formation.service;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.excilys.formation.cli.Controller;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.implementation.ComputerServiceImpl;

public class ComputerServiceImplTest {
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testGetInstance() {
        ComputerService computerService = ComputerServiceImpl.getInstance();
        assertNotNull("Get instance : not null", computerService);
        assertEquals("Get instance : good class name", computerService.getClass().getSimpleName(), "ComputerServiceImpl");
    }
    @Test
    public void testCreate() {
        Controller controller = new Controller();
        ComputerDto computerDto = new ComputerDto();
        computerDto.name = "Test Service";
        computerDto.companyId = 7;
        ComputerDto computerDtoTest = null;
        computerDto = controller.createComputer(computerDto);
        computerDtoTest = controller.getComputerById(computerDto.id);
        assertNotNull("Create : not null", computerDtoTest);
        assertTrue("Create : valid id", computerDtoTest.id >= 1);
        assertEquals("Create : equals name", computerDto.name, computerDtoTest.name);
        assertEquals("Create : equals companies id", computerDto.companyId, computerDtoTest.companyId);
    }
    @Test
    public void testDelete() throws ServiceException {
        Controller controller = new Controller();
        ComputerDto computerDto = new ComputerDto();
        computerDto.name = "Test Service";
        computerDto.companyId = 7;
        ComputerDto computerDtoTest = null;
        computerDto = controller.createComputer(computerDto);
        controller.deleteComputer(computerDto.id);
        computerDtoTest = controller.getComputerById(computerDto.id);
        assertTrue("Delete ", computerDtoTest == null);
    }
    @Test
    public void testGetById() {
        Controller controller = new Controller();
        ComputerDto computerDto = controller.getComputerById(5);
        assertNotNull("Get by id : not null", computerDto);
        assertTrue("Get by id : good id", computerDto.id == 5);
        assertNotNull("Get by id : name not null", computerDto.name);
    }
    @Test
    public void testGetPage() {
        Controller controller = new Controller();
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
        Page<ComputerDto> page = new Page<>(10);
        page = controller.getPageComputer(pageFilter);
        assertNotNull("Get page : page not null", page);
        assertNotNull("Get page : list not null", page.elems);
        assertNotNull("Get page : first element not null", page.elems.get(0));
        assertEquals("Get page : first element Company", page.elems.get(0).getClass().getSimpleName(), "ComputerDto");
        assertNotNull("Get page : last element not null", page.elems.get(page.elems.size()-1));
        assertEquals("Get page : last element Company", page.elems.get(page.elems.size()-1).getClass().getSimpleName(), "ComputerDto");
    }
    @Test
    public void testUpdate() {
        Controller controller = new Controller();
        ComputerDto computerDto = new ComputerDto();
        computerDto.name = "Test Service";
        computerDto.companyId = 7;
        ComputerDto computerDtoTest = null;
        computerDto = controller.createComputer(computerDto);
        computerDto.name = "New name";
        controller.updateComputer(computerDto);
        computerDtoTest = controller.getComputerById(computerDto.id);
        assertNotNull("Update : not null", computerDtoTest);
        assertEquals("Update :  good name", computerDtoTest.name, "New name");
    }
}
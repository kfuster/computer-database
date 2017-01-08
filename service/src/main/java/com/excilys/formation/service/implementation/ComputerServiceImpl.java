package com.excilys.formation.service.implementation;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.service.ComputerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for Computers.
 * @author kfuster
 *
 */
@Service
public class ComputerServiceImpl implements ComputerService {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ComputerServiceImpl.class);
    @Autowired
    private ComputerDao computerDao;

    @Override
    @Transactional
    public Computer create(Computer pComputer) {
        return computerDao.create(pComputer);
    }

    @Override
    @Transactional
    public void delete(long pId) {
        computerDao.delete(pId);
    }

    @Override
    @Transactional
    public void deleteList(List<Long> idList) {
        computerDao.deleteList(idList);
    }

    @Override
    @Transactional(readOnly = true)
    public Computer getById(long pId) {
        return computerDao.getById(pId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Computer> getPage(PageFilter pViewDto) {
        return computerDao.getPage(pViewDto);
    }

    @Override
    @Transactional
    public void update(Computer pComputer) {
        computerDao.update(pComputer);
    }
}
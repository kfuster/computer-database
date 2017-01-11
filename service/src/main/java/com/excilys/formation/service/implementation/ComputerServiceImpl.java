package com.excilys.formation.service.implementation;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.service.ComputerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for Computers.
 * @author kfuster
 */
@Service
public class ComputerServiceImpl implements ComputerService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ComputerServiceImpl.class);

    @Autowired
    private ComputerDao computerDao;

    @Override
    @Transactional
    @CacheEvict(value = "cachePages", allEntries = true)
    public Computer create(Computer computer) {
        return computerDao.create(computer);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cachePages", allEntries = true)
    public void delete(long id) {
        computerDao.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cachePages", allEntries = true)
    public void deleteList(List<Long> idList) {
        computerDao.deleteList(idList);
    }

    @Override
    @Transactional(readOnly = true)
    public Computer getById(long id) {
        return computerDao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cachePages", keyGenerator = "keyGenerator")
    public Page<Computer> getPage(PageFilter viewDto) {
        return computerDao.getPage(viewDto);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cachePages", allEntries = true)
    public void update(Computer computer) {
        computerDao.update(computer);
    }
}
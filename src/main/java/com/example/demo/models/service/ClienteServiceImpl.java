package com.example.demo.models.service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.models.dao.IClienteDao;
import com.example.demo.models.dao.IFacturaDao;
import com.example.demo.models.dao.IProductoDao;
import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.Factura;
import com.example.demo.models.entity.Producto;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	@Override
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	public Cliente findOne(Long id) {
//		Optional<Cliente> optinalCliente =  clienteDao.findById(id);
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente fetchByIdWithFacturas(Long id) {
		return clienteDao.fetchByIdWithFacturas(id);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	//Factura
	@Override
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombre(term);
		//return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional
	public Producto findProductoById(Long id) {
		Optional<Producto> optinalProducto =  productoDao.findById(id);
		return optinalProducto.orElse(null);
	}

	@Override
	@Transactional
	public Factura findFacturaById(Long id) {
		Optional<Factura> optinalFactura =  facturaDao.findById(id);
		return optinalFactura.orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long idFactura) {
		return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(idFactura);
	}

}

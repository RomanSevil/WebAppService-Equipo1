package com.WebAppService.Equipo1.controladores;
import com.WebAppService.Equipo1.entidad.*;
import com.WebAppService.Equipo1.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    ProveedorServicio proveedorServicio;
    
    @Autowired
    ImagenServicio imagenServicio;
    
    

    @GetMapping("/proveedor/{id}")
    public ResponseEntity<byte[]> imagenProveedor(@PathVariable Integer id) {
        Proveedor proveedor = proveedorServicio.getOne(id);
        Imagen imagen = proveedor.getImagen();

        if (imagen != null) {
            byte[] contenidoImagen = imagen.getContenido();
            String tipoMime = imagen.getMime();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(tipoMime));

            return new ResponseEntity<>(contenidoImagen, headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> imagenPorId(@PathVariable String id) {
        
        Imagen imagen = imagenServicio.obtenerImagenPorId(id);

        if (imagen != null) {
            byte[] contenidoImagen = imagen.getContenido();
            String tipoMime = imagen.getMime();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(tipoMime));

            return new ResponseEntity<>(contenidoImagen, headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//@GetMapping("/usuario/{id}")
//    public ResponseEntity<byte[]> imagenUsuario(@PathVariable Integer id) {
//        Usuario usuario = usuarioServicio.getOne(id);
//        Imagen imagen = usuario.getImagen();
//
//        if (imagen != null) {
//            byte[] contenidoImagen = imagen.getContenido();
//            String tipoMime = imagen.getMime();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType(tipoMime));
//
//            return new ResponseEntity<>(contenidoImagen, headers, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping("/perfil/{id}")
//    public ResponseEntity<byte[]> imagenUsuario (@PathVariable String id){
//        Usuario usuario = usuarioServicio.getOne(id);
//        
//       byte[] imagen= usuario.getImagen().getContenido();
//       
//       HttpHeaders headers = new HttpHeaders();
//       
//       headers.setContentType(MediaType.IMAGE_JPEG);
//       
//        
//        
//       return new ResponseEntity<>(imagen,headers, HttpStatus.OK); 
//    }
    
}
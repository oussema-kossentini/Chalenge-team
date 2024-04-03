package tn.esprit.spring.courszelloback;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public class MultipartFileToByteArrayConverter implements Converter<MultipartFile, byte[]> {
    @Override
    public byte[] convert(MultipartFile source) {
        if (source != null) {
            try {
                return source.getBytes();
            } catch (IOException e) {
                throw new RuntimeException("Échec de la conversion du fichier en byte[]", e);
            }
        }
        return null; // ou vous pouvez choisir de lancer une exception si le fichier est obligatoire
    }


}

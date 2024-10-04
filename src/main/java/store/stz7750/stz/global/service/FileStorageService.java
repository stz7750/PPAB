package store.stz7750.stz.global.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * packageName    : stz-store.stz7750.stz.global.service
 * fileName       : FileStorageService
 * author         : stz
 * date           : 10/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/4/24        stz       최초 생성
 */
@Service
public class FileStorageService  {

    @Value("${file.upload-dir}")
    private String uploadDir;

        public String storeFile(MultipartFile file) throws IOException {
            // 저장할 디렉토리 경로를 가져와 Path 객체로 생성
            Path dirPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            // 디렉토리가 존재하지 않으면 생성
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // 파일 이름을 유니크하게 생성하거나 원본 파일 이름 사용
            String fileName = new String(file.getOriginalFilename().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            Path filePath = dirPath.resolve(fileName);

            // 파일을 저장
            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                return fileName; // 저장된 파일 이름 반환
            } catch (IOException ex) {
                throw new IOException("사진 저장 실패" + fileName, ex);
            }
        }
}



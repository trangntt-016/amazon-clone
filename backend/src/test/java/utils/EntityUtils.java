package utils;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public class EntityUtils {
    public <T> T generateRandomEntity(JpaRepository<T, Integer> repo, Integer first){
        // please check first index in the database, using generics prohibit from getting the first index in entity
        int last = repo.findAll().size();
        T object = null;
        Boolean found = false;
        while(!found){
            int randomNumber = generateRandomNumber(first, first+last);
            try {
                object = repo.findById(randomNumber).orElseThrow(()->new NotFoundException("Found nothing with this id"));
                if(object!=null){
                    found = true;
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        return object;
    }

    public static int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
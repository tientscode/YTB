package com.tscocde.YTB.Repository;


import com.tscocde.YTB.Entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Videos,Long> {
    List<Videos> getAllBy();
    Videos save(Videos video);

}

package com.test.webproject1.servises;


import com.test.webproject1.DTO.PostDTO;
import com.test.webproject1.DTO.PostDTOForAllView;
import com.test.webproject1.entities.Address;
import com.test.webproject1.entities.Picture;
import com.test.webproject1.entities.Post;
import com.test.webproject1.entities.User;
import com.test.webproject1.repositories.AddressRepository;
import com.test.webproject1.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final AddressRepository addressRepository;

    private final PictureService pictureService;

    public Post saveNewPost(PostDTO postData, User user) throws IOException {
        Post post = postRepository.save(new Post(postData.getPostName().trim(), LocalDate.now(), postData.getDescription(), user));

        Address address = new Address(post,postData.getCity().trim(), postData.getFullAddress().trim());
        addressRepository.save(address);
        pictureService.savePostPicture(post , postData.getMainImage());
        return post;
    }

    public Long getPostCreatorId(int PostsId){
        return postRepository.getById(PostsId).getUser().getId();
    }

    public ArrayList<PostDTOForAllView> getAllPostsByUserId(Long id){
        List<Post> postList = postRepository.findAllByUserId(id);
        return transferDataToDTO(postList);
    }

    public ArrayList<PostDTOForAllView> getAllPosts(){
        List<Post> postList = postRepository.findAll();
        return transferDataToDTO(postList);
    }

    //
    public ArrayList<PostDTOForAllView> transferDataToDTO(List<Post> postList){
        ArrayList<PostDTOForAllView> postDTOForAllViewList = new ArrayList<>();

        for (int i = 0; i < postList.size(); i++) {
            postDTOForAllViewList.add(new PostDTOForAllView(postList.get(i).getId(),postList.get(i).getPostName(),postList.get(i).getAddress().getCity(),
                    postList.get(i).getDateOfPost(), pictureService.getPostPictures(postList.get(i).getId()).get(0).getPictureFullPath()));
        }
        return postDTOForAllViewList;
    }

    public void changePostInfoById(int id, String postName, String city, String fullAddress, String description){

        if (!postName.equals(""))
            postRepository.setPostName(id, postName);

        if (!city.equals(""))
            addressRepository.setCityById(id, city);

        if (!fullAddress.equals(""))
            addressRepository.setFullAddressById(id,fullAddress);

        if (!description.equals(""))
            postRepository.setPostDescription(id, description);
    }
}

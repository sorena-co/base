package ir.sp.base.service;

import ir.sp.base.domain.Blog;
import ir.sp.base.repository.BlogRepository;
import ir.sp.base.service.dto.BlogDTO;
import ir.sp.base.service.mapper.BlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Blog.
 */
@Service
@Transactional
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;

    public BlogService(BlogRepository blogRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
    }

    /**
     * Save a blog.
     *
     * @param blogDTO the entity to save
     * @return the persisted entity
     */
    public BlogDTO save(BlogDTO blogDTO) {
        log.debug("Request to save Blog : {}", blogDTO);
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        return blogMapper.toDto(blog);
    }

    /**
     * Get all the blogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BlogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Blogs");
        return blogRepository.findAll(pageable)
            .map(blogMapper::toDto);
    }

    /**
     * Get one blog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public BlogDTO findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        Blog blog = blogRepository.findOne(id);
        return blogMapper.toDto(blog);
    }

    /**
     * Delete the blog by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.delete(id);
    }
}

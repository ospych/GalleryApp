
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.galleryapp.core.network.interceptors.NetworkErrorException
import com.example.galleryapp.core.services.ImagesApi
import com.example.galleryapp.core.services.models.PhotoResponse

private const val STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 10


class ImagesPagingSource(
    private val service: ImagesApi
) : PagingSource<Int, PhotoResponse>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoResponse> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getImages(
                page = pageIndex,
                perPage = NETWORK_PAGE_SIZE
            )
            val photos = response.body() ?: emptyList()
            val nextKey =
                if (photos.isEmpty()) {
                    null
                } else {
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = photos,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: NetworkErrorException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
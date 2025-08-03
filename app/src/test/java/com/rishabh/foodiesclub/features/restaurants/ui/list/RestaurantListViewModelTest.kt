package com.rishabh.foodiesclub.features.restaurants.ui.list

import com.rishabh.foodiesclub.core.data.Resource
import com.rishabh.foodiesclub.features.restaurants.domain.model.Deal
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import com.rishabh.foodiesclub.features.restaurants.domain.usecase.GetRestaurantsUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RestaurantListViewModelTest {

    // Dispatcher for controlling time in tests
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getRestaurantsUseCase: GetRestaurantsUseCase

    private lateinit var viewModel: RestaurantListViewModel

    @Before
    fun setUp() {
        // Replace the main dispatcher with our test dispatcher
        Dispatchers.setMain(testDispatcher)
        getRestaurantsUseCase = mockk()
    }

    @Test
    fun `onSearchQueryChanged - filters and sorts restaurants after debounce`() = runTest {
        // Given a list of restaurants
        val restaurants = listOf(
            sampleRestaurant.copy(id = "1", name = "Pizza Place", deals = listOf(deal.copy(discountPercent = 20))),
            sampleRestaurant.copy(id = "2", name = "Sushi Spot", deals = listOf(deal.copy(discountPercent = 50))),
            sampleRestaurant.copy(id = "3", name = "Another Pizza Joint", deals = listOf(deal.copy(discountPercent = 30)))
        )

        every { getRestaurantsUseCase() } returns flowOf(Resource.Success(restaurants))
        viewModel = RestaurantListViewModel(getRestaurantsUseCase, testDispatcher)

        // When the user types "Pizza" into the search bar
        viewModel.onSearchQueryChanged("Pizza")
        advanceTimeBy(500)

        // Then get the final state from the ViewModel
        val finalState = viewModel.uiState.value
        // Assert that only the two "Pizza" restaurants are in the list
        assertEquals(2, finalState.restaurants.size)
        // Assert that the results are sorted by the best deal (30% off before 20% off)
        assertEquals("3", finalState.restaurants.first().id)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to its original state
        Dispatchers.resetMain()
    }

    private val deal
        get() = Deal(
            id = "d1", discountPercent = 40, isDineIn = true, isLightningDeal = false,
            startTime = "5:00pm", endTime = "7:00pm", quantityLeft = 3
        )

    private val sampleRestaurant
        get() = Restaurant(
            id = "1", name = "Masala Kitchen", address = "123 Test Street", suburb = "Sydney",
            cuisines = listOf("Indian", "Test", "Data"), imageUrl = "", openTime = "12:00pm",
            closeTime = "11:00pm", deals = listOf(deal)
        )
}
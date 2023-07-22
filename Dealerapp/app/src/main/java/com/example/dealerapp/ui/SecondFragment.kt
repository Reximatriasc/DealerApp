package com.example.dealerapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dealerapp.application.DealerApplication
import com.example.dealerapp.databinding.FragmentSecondBinding
import com.example.dealerapp.model.Dealer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val dealerViewModel: DealerappViewModel by viewModels {
        DealerViewModelFactory((applicationContext as DealerApplication).repository) }
    private val args: SecondFragmentArgs by navArgs()
    private var dealer: Dealer? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLng: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dealer = args.dealer

        if (dealer != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Update"
            binding.NamaDealerEditText.setText(dealer?.Nama)
            binding.AlamatEditText.setText(dealer?.Alamat)
            binding.TelpEditText.setText(dealer?.Telp)
        }

        // binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(com.example.dealerapp.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        binding.saveButton.setOnClickListener {
            val Nama = binding.NamaDealerEditText.text.toString()
            val Alamat = binding.AlamatEditText.text.toString()
            val Telp = binding.TelpEditText.text.toString()

            if (Nama.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (Alamat.isEmpty()) {
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (Telp.isEmpty()) {
                Toast.makeText(context, "Telp tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dealer == null) {
                val newDealer = Dealer(0, Nama, Alamat, Telp, currentLatLng?.latitude, currentLatLng?.longitude)
                dealerViewModel.insert(newDealer)
            } else {
                val updatedDealer = Dealer(dealer!!.id, Nama, Alamat, Telp, currentLatLng?.latitude, currentLatLng?.longitude)
                dealerViewModel.update(updatedDealer)
            }
            findNavController().popBackStack()
        }

        binding.deleteButton.setOnClickListener {
            dealer?.let { dealerViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true

        val sydney = LatLng(-34.0, 151.0)
        val markerOption = MarkerOptions()
            .position(sydney)
            .title("Test")
            .draggable(true)
            .icon(BitmapDescriptorFactory.fromResource(com.example.dealerapp.R.drawable.ic_flat_tire))
        mMap.addMarker(markerOption)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDragStart(marker: Marker) {
    }
    override fun onMarkerDrag(marker: Marker) {
    }
    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLng = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLng.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun checkPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(applicationContext, "Akses Lokasi Ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    currentLatLng = latLng
                    var title = "Marker"

                    if (dealer != null) {
                        title = dealer!!.Nama
                        val newCurrentLocation = LatLng(dealer!!.latitude!!, dealer!!.longitude!!)
                        currentLatLng = newCurrentLocation
                    }

                    val markerOptions = MarkerOptions()
                        .position(latLng)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(com.example.dealerapp.R.drawable.ic_flat_tire))
                    mMap.addMarker(markerOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
            }
    }
}

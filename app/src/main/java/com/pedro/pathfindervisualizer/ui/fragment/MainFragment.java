package com.pedro.pathfindervisualizer.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pedro.pathfindervisualizer.R;
import com.pedro.pathfindervisualizer.databinding.FragmentMainBinding;
import com.pedro.pathfindervisualizer.enums.PointStatusEnum;
import com.pedro.pathfindervisualizer.model.Point;
import com.pedro.pathfindervisualizer.pathfinder.AbstractPathfinder;
import com.pedro.pathfindervisualizer.pathfinder.DijkstraPathfinder;
import com.pedro.pathfindervisualizer.pathfinder.IPathfinderInfo;
import com.pedro.pathfindervisualizer.pathfinder.PathfinderTypeEnum;
import com.pedro.pathfindervisualizer.ui.viewmodel.MainViewModel;
import com.pedro.pathfindervisualizer.utils.ThreadUtils;

public class MainFragment extends Fragment {
    private AbstractPathfinder pathfinder;
    private FragmentMainBinding binding;
    private MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        getLifecycle().addObserver(viewModel);
        viewModel.getPathfinderTypeEnum().observe(getViewLifecycleOwner(), pathfinderTypeEnum -> {
            pathfinder = getPathfinder(pathfinderTypeEnum);
        });
        viewModel.getSelectedOptionEnum().observe(getViewLifecycleOwner(), selectedOptionEnum -> {
            processSelectedOption(selectedOptionEnum);
        });
        viewModel.getAnimationTime().observe(getViewLifecycleOwner(), animationTime -> {
            binding.pathfinderBoard.setAnimationTime(animationTime);
        });
        return binding.getRoot();
    }

    private AbstractPathfinder getPathfinder(PathfinderTypeEnum pathfinderTypeEnum) {
        switch (pathfinderTypeEnum) {
            case DIJKSTRA:
                return new DijkstraPathfinder(getPathfinderInfo());
            default:
                return new DijkstraPathfinder(getPathfinderInfo());
        }
    }

    private void processSelectedOption(MainViewModel.SelectedOptionEnum selectedOptionEnum) {
        if (pathfinder.isRunning()) {
            return;
        }
        switch (selectedOptionEnum) {
            case START:
                ThreadUtils.run(() -> {
                    binding.pathfinderBoard.setBlocked(true);
                    pathfinder.getFinalPath(binding.pathfinderBoard.getBegin(), binding.pathfinderBoard.getEnd());
                });
                break;
            case CLEAR:
                binding.pathfinderBoard.clear();
                binding.pathfinderBoard.setBlocked(false);
            default:
                break;
        }
    }

    private IPathfinderInfo getPathfinderInfo() {
        return new IPathfinderInfo() {
            @Override
            public boolean validPosition(Point point) {
                Point limit = binding.pathfinderBoard.boardLimit();
                return point.getX() >= 0 && point.getY() >= 0 && point.getX() <= limit.getX() && point.getY() <= limit.getY() && !pointStatus(point).equals(PointStatusEnum.WALL);
            }

            @Override
            public PointStatusEnum pointStatus(Point point) {
                return binding.pathfinderBoard.pointStatus(point);
            }

            @Override
            public void updatePosition(Point point, PointStatusEnum pointStatusEnum) {
                binding.pathfinderBoard.updatePoint(point, pointStatusEnum);
            }
        };
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }
}
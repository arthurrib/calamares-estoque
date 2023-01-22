import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FornecedorFormService, FornecedorFormGroup } from './fornecedor-form.service';
import { IFornecedor } from '../fornecedor.model';
import { FornecedorService } from '../service/fornecedor.service';

@Component({
  selector: 'jhi-fornecedor-update',
  templateUrl: './fornecedor-update.component.html',
})
export class FornecedorUpdateComponent implements OnInit {
  isSaving = false;
  fornecedor: IFornecedor | null = null;

  editForm: FornecedorFormGroup = this.fornecedorFormService.createFornecedorFormGroup();

  constructor(
    protected fornecedorService: FornecedorService,
    protected fornecedorFormService: FornecedorFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fornecedor }) => {
      this.fornecedor = fornecedor;
      if (fornecedor) {
        this.updateForm(fornecedor);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fornecedor = this.fornecedorFormService.getFornecedor(this.editForm);
    if (fornecedor.id !== null) {
      this.subscribeToSaveResponse(this.fornecedorService.update(fornecedor));
    } else {
      this.subscribeToSaveResponse(this.fornecedorService.create(fornecedor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedor>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(fornecedor: IFornecedor): void {
    this.fornecedor = fornecedor;
    this.fornecedorFormService.resetForm(this.editForm, fornecedor);
  }
}

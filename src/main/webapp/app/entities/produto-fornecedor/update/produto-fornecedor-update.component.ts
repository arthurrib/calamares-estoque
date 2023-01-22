import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProdutoFornecedorFormService, ProdutoFornecedorFormGroup } from './produto-fornecedor-form.service';
import { IProdutoFornecedor } from '../produto-fornecedor.model';
import { ProdutoFornecedorService } from '../service/produto-fornecedor.service';
import { IProduto } from 'app/entities/produto/produto.model';
import { ProdutoService } from 'app/entities/produto/service/produto.service';
import { IFornecedor } from 'app/entities/fornecedor/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor/service/fornecedor.service';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { PedidoService } from 'app/entities/pedido/service/pedido.service';

@Component({
  selector: 'jhi-produto-fornecedor-update',
  templateUrl: './produto-fornecedor-update.component.html',
})
export class ProdutoFornecedorUpdateComponent implements OnInit {
  isSaving = false;
  produtoFornecedor: IProdutoFornecedor | null = null;

  produtosCollection: IProduto[] = [];
  fornecedorsCollection: IFornecedor[] = [];
  pedidosSharedCollection: IPedido[] = [];

  editForm: ProdutoFornecedorFormGroup = this.produtoFornecedorFormService.createProdutoFornecedorFormGroup();

  constructor(
    protected produtoFornecedorService: ProdutoFornecedorService,
    protected produtoFornecedorFormService: ProdutoFornecedorFormService,
    protected produtoService: ProdutoService,
    protected fornecedorService: FornecedorService,
    protected pedidoService: PedidoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProduto = (o1: IProduto | null, o2: IProduto | null): boolean => this.produtoService.compareProduto(o1, o2);

  compareFornecedor = (o1: IFornecedor | null, o2: IFornecedor | null): boolean => this.fornecedorService.compareFornecedor(o1, o2);

  comparePedido = (o1: IPedido | null, o2: IPedido | null): boolean => this.pedidoService.comparePedido(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produtoFornecedor }) => {
      this.produtoFornecedor = produtoFornecedor;
      if (produtoFornecedor) {
        this.updateForm(produtoFornecedor);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produtoFornecedor = this.produtoFornecedorFormService.getProdutoFornecedor(this.editForm);
    if (produtoFornecedor.id !== null) {
      this.subscribeToSaveResponse(this.produtoFornecedorService.update(produtoFornecedor));
    } else {
      this.subscribeToSaveResponse(this.produtoFornecedorService.create(produtoFornecedor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProdutoFornecedor>>): void {
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

  protected updateForm(produtoFornecedor: IProdutoFornecedor): void {
    this.produtoFornecedor = produtoFornecedor;
    this.produtoFornecedorFormService.resetForm(this.editForm, produtoFornecedor);

    this.produtosCollection = this.produtoService.addProdutoToCollectionIfMissing<IProduto>(
      this.produtosCollection,
      produtoFornecedor.produto
    );
    this.fornecedorsCollection = this.fornecedorService.addFornecedorToCollectionIfMissing<IFornecedor>(
      this.fornecedorsCollection,
      produtoFornecedor.fornecedor
    );
    this.pedidosSharedCollection = this.pedidoService.addPedidoToCollectionIfMissing<IPedido>(
      this.pedidosSharedCollection,
      produtoFornecedor.produtoFornecedor
    );
  }

  protected loadRelationshipsOptions(): void {
    this.produtoService
      .query({ filter: 'produtofornecedor-is-null' })
      .pipe(map((res: HttpResponse<IProduto[]>) => res.body ?? []))
      .pipe(
        map((produtos: IProduto[]) =>
          this.produtoService.addProdutoToCollectionIfMissing<IProduto>(produtos, this.produtoFornecedor?.produto)
        )
      )
      .subscribe((produtos: IProduto[]) => (this.produtosCollection = produtos));

    this.fornecedorService
      .query({ filter: 'produtofornecedor-is-null' })
      .pipe(map((res: HttpResponse<IFornecedor[]>) => res.body ?? []))
      .pipe(
        map((fornecedors: IFornecedor[]) =>
          this.fornecedorService.addFornecedorToCollectionIfMissing<IFornecedor>(fornecedors, this.produtoFornecedor?.fornecedor)
        )
      )
      .subscribe((fornecedors: IFornecedor[]) => (this.fornecedorsCollection = fornecedors));

    this.pedidoService
      .query()
      .pipe(map((res: HttpResponse<IPedido[]>) => res.body ?? []))
      .pipe(
        map((pedidos: IPedido[]) =>
          this.pedidoService.addPedidoToCollectionIfMissing<IPedido>(pedidos, this.produtoFornecedor?.produtoFornecedor)
        )
      )
      .subscribe((pedidos: IPedido[]) => (this.pedidosSharedCollection = pedidos));
  }
}
